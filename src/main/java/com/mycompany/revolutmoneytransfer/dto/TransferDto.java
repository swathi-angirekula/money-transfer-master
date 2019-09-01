package com.mycompany.revolutmoneytransfer.dto;

import com.mycompany.revolutmoneytransfer.model.Transfer;
import com.mycompany.revolutmoneytransfer.model.Currency;
import com.mycompany.revolutmoneytransfer.model.ExceptionType;
import com.mycompany.revolutmoneytransfer.model.TransferStatus;
import com.mycompany.revolutmoneytransfer.model.BankAccount;
import com.mycompany.revolutmoneytransfer.db.DbUtils;
import com.mycompany.revolutmoneytransfer.db.H2DataSource;
import com.mycompany.revolutmoneytransfer.exceptions.ImpossibleOperationExecution;
import com.mycompany.revolutmoneytransfer.exceptions.ObjectModificationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;


public class TransferDto {
    private static final Logger log = LoggerFactory.getLogger(TransferDto.class);

    private static final String TRANSFER_TABLE_NAME = "transfer";
    private static final String TRANSFER_ID_ROW = "id";
    private static final String TRANSFER_FROM_ACCOUNT_ROW = "from_account_id";
    private static final String TRANSFER_TO_ACCOUNT_ROW = "to_account_id";
    private static final String TRANSFER_AMOUNT_ROW = "amount";
    private static final String TRANSFER_CURRENCY_ROW = "currency_id";
    private static final String TRANSFER_CREATION_DATE_ROW = "creation_date";
    private static final String TRANSFER_UPDATE_DATE_ROW = "update_date";
    private static final String TRANSFER_STATUS_ROW = "status_id";
    private static final String FAIL_MESSAGE_ROW = "failMessage";

    public static final String GET_ALL_TRANSFERS_SQL = "select * from " + TRANSFER_TABLE_NAME;
    public static final String GET_TRANSFERS_BY_STATUS_SQL =
            "select id from " + TRANSFER_TABLE_NAME + " trans " +
                    "where trans." + TRANSFER_STATUS_ROW + " = ?";
    public static final String GET_TRANSFERS_BY_ID_SQL =
            "select * from " + TRANSFER_TABLE_NAME + " trans " +
                    "where trans." + TRANSFER_ID_ROW + " = ?";
    public static final String GET_TRANSFERS_FOR_UPDATE_BY_ID_SQL =
            GET_TRANSFERS_BY_ID_SQL + " for update";

    private static TransferDto transferDto;
    private BankAccountDto bankAccountDto = BankAccountDto.getInstance();
    private DbUtils dbUtils = DbUtils.getInstance();

    private TransferDto() {
    }

    //Just for testing purpose
    TransferDto(DbUtils dbUtils) {
        this.dbUtils = dbUtils;
    }

    public static TransferDto getInstance() {
        if(transferDto == null){
            synchronized (TransferDto.class) {
                if(transferDto == null){
                    transferDto = new TransferDto();
                }
            }
        }
        return transferDto;
    }


    public Collection<Transfer> getAllTransfers() {
        return dbUtils.executeQuery(GET_ALL_TRANSFERS_SQL, getAllTransfers -> {
            Collection<Transfer> transfers = new ArrayList<>();

            try (ResultSet transfersRS = getAllTransfers.executeQuery()) {
                if (transfersRS != null) {
                    while (transfersRS.next()) {
                        transfers.add(extractTransactionFromResultSet(transfersRS));
                    }
                }
            }

            return transfers;
        }).getResult();
    }


    public Collection<Long> getAllTransactionIdsByStatus(TransferStatus transferStatus) {
        if (transferStatus == null) {
            return null;
        }

        return dbUtils.executeQuery(GET_TRANSFERS_BY_STATUS_SQL, getTransfersByStatus -> {
            Collection<Long> transferIds = new ArrayList<>();

            getTransfersByStatus.setLong(1, transferStatus.getId());
            try (ResultSet transfersRS = getTransfersByStatus.executeQuery()) {
                if (transfersRS != null) {
                    while (transfersRS.next()) {
                        transferIds.add(transfersRS.getLong(TRANSFER_ID_ROW));
                    }
                }
            }

            return transferIds;
        }).getResult();
    }


    public Transfer getTransactionById(Long id) {
        return dbUtils.executeQuery(GET_TRANSFERS_BY_ID_SQL, getTransactionById -> {
            getTransactionById.setLong(1, id);
            try (ResultSet transferRS = getTransactionById.executeQuery()) {
                if (transferRS != null && transferRS.first()) {
                    return extractTransactionFromResultSet(transferRS);
                }
            }

            return null;
        }).getResult();
    }


    public Transfer createTransaction(Transfer transfer) throws ObjectModificationException {
        String INSERT_TRANSFER_SQL =
                "insert into " + TRANSFER_TABLE_NAME +
                        " (" +
                        TRANSFER_FROM_ACCOUNT_ROW + ", " +
                        TRANSFER_TO_ACCOUNT_ROW + ", " +
                        TRANSFER_AMOUNT_ROW + ", " +
                        TRANSFER_CURRENCY_ROW + ", " +
                        TRANSFER_STATUS_ROW + ", " +
                        FAIL_MESSAGE_ROW + ", " +
                        TRANSFER_CREATION_DATE_ROW + ", " +
                        TRANSFER_UPDATE_DATE_ROW +
                        ") values (?, ?, ?, ?, ?, ?, ?, ?)";

        verify(transfer);

        Connection con = H2DataSource.getConnection();

        try {
            BankAccount fromBankAccount = bankAccountDto.
                    getForUpdateBankAccountById(con, transfer.getFromBankAccountId());

            BigDecimal amountToWithdraw = transfer.getAmount();
            //Check that from bank account has enough money
            if (fromBankAccount.getBalance().subtract(fromBankAccount.getBlockedAmount())
                    .compareTo(amountToWithdraw) < 0) {
                throw new ObjectModificationException(ExceptionType.OBJECT_IS_MALFORMED,
                        "The specified bank account could not transfer this amount of money. " +
                                "His balance does not have enough money");
            }

            fromBankAccount.setBlockedAmount(fromBankAccount.getBlockedAmount().add(amountToWithdraw));

            bankAccountDto.updateBankAccount(fromBankAccount, con);

            transfer = dbUtils.executeQueryInConnection(con, INSERT_TRANSFER_SQL,
                    new DbUtils.CreationQueryExecutor<>(transfer, TransferDto::fillInPreparedStatement)).getResult();

            if (transfer == null) {
                throw new ObjectModificationException(ExceptionType.COULD_NOT_OBTAIN_ID);
            }

            con.commit();
        } catch (RuntimeException | SQLException e) {
            DbUtils.safeRollback(con);
            log.error("Unexpected exception", e);
            throw new ImpossibleOperationExecution(e);
        } finally {
            DbUtils.quietlyClose(con);
        }

        return transfer;

    }

    public void executeTransaction(Long id) throws ObjectModificationException {
        if (id == null) {
            throw new ObjectModificationException(ExceptionType.OBJECT_IS_MALFORMED,
                    "The specified transfer doesn't exists");
        }

        Connection con = H2DataSource.getConnection();

        Transfer transfer = null;
        try {
            transfer = getForUpdateTransactionById(id, con);

            if (transfer.getStatus() != TransferStatus.INITIATED) {
                throw new ObjectModificationException(ExceptionType.OBJECT_IS_MALFORMED,
                        "Could not execute transfer which is not in INITIATED status");
            }

            BankAccount fromBankAccount = bankAccountDto.
                    getForUpdateBankAccountById(con, transfer.getFromBankAccountId());

            BankAccount toBankAccount = bankAccountDto.
                    getForUpdateBankAccountById(con, transfer.getToBankAccountId());

            BigDecimal amountToWithdraw = transfer.getAmount();
            BigDecimal newBlockedAmount = fromBankAccount.getBlockedAmount().subtract(amountToWithdraw);
            BigDecimal newBalance = fromBankAccount.getBalance().subtract(amountToWithdraw);

            if (newBlockedAmount.compareTo(BigDecimal.ZERO) < 0 || newBalance.compareTo(BigDecimal.ZERO) < 0) {
                transfer.setStatus(TransferStatus.FAILED);
                transfer.setFailMessage(String.format("There is no enough money. Current balance is %f",
                        fromBankAccount.getBalance().doubleValue()));
            } else {
                fromBankAccount.setBlockedAmount(newBlockedAmount);
                fromBankAccount.setBalance(newBalance);

                bankAccountDto.updateBankAccount(fromBankAccount, con);

                BigDecimal amountToTransfer = transfer.getAmount();

                toBankAccount.setBalance(toBankAccount.getBalance().add(amountToTransfer));

                bankAccountDto.updateBankAccount(toBankAccount, con);

                transfer.setStatus(TransferStatus.SUCCESSFUL);
            }

            updateTransaction(transfer, con);

            con.commit();
        } catch (RuntimeException | SQLException e) {
            DbUtils.safeRollback(con);
            if (transfer != null) {
                transfer.setStatus(TransferStatus.FAILED);
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
                String exceptionAsString = String.format("Transaction has been rolled back as it was unexpected exception: %s",
                        sw.toString()).substring(0, 4000);
                transfer.setFailMessage(exceptionAsString);
                updateTransaction(transfer);
            }
            log.error("Unexpected exception", e);
            throw new ImpossibleOperationExecution(e);
        } finally {
            DbUtils.quietlyClose(con);
        }
    }

    private Transfer getForUpdateTransactionById(Long id, Connection con) {
        return dbUtils.executeQueryInConnection(con, GET_TRANSFERS_FOR_UPDATE_BY_ID_SQL, getTransaction -> {
            getTransaction.setLong(1, id);
            try (ResultSet transferRS = getTransaction.executeQuery()) {
                if (transferRS != null && transferRS.first()) {
                    return extractTransactionFromResultSet(transferRS);
                }
            }

            return null;
        }).getResult();
    }


    private void updateTransaction(Transfer transfer) throws ObjectModificationException {
        updateTransaction(transfer, null);
    }


    private void updateTransaction(Transfer transfer, Connection con) throws ObjectModificationException {
        String UPDATE_TRANSFER_SQL =
                "update " + TRANSFER_TABLE_NAME +
                        " set " +
                        TRANSFER_STATUS_ROW + " = ?, " +
                        FAIL_MESSAGE_ROW + " = ?, " +
                        TRANSFER_UPDATE_DATE_ROW + " = ? " +
                        "where " + TRANSFER_ID_ROW + " = ?";

        verify(transfer);

        DbUtils.QueryExecutor<Integer> queryExecutor = updateTransaction -> {
            updateTransaction.setInt(1, transfer.getStatus().getId());
            updateTransaction.setString(2, transfer.getFailMessage());
            updateTransaction.setDate(3, new Date(new java.util.Date().getTime()));
            updateTransaction.setLong(4, transfer.getId());

            return updateTransaction.executeUpdate();
        };

        int result;
        if (con == null) {
            result = dbUtils.executeQuery(UPDATE_TRANSFER_SQL, queryExecutor).getResult();
        } else {
            result = dbUtils.executeQueryInConnection(con, UPDATE_TRANSFER_SQL, queryExecutor).getResult();
        }

        if (result == 0) {
            throw new ObjectModificationException(ExceptionType.OBJECT_IS_NOT_FOUND);
        }
    }

    private void verify(Transfer transfer) throws ObjectModificationException {
        if (transfer.getAmount() == null || transfer.getFromBankAccountId() == null ||
                transfer.getToBankAccountId() == null || transfer.getCurrency() == null
                || transfer.getStatus() == null || transfer.getCreationDate() == null
                || transfer.getUpdateDate() == null) {
            throw new ObjectModificationException(ExceptionType.OBJECT_IS_MALFORMED, "Fields could not be NULL");
        }
    }

    private static void fillInPreparedStatement(PreparedStatement preparedStatement, Transfer transfer) {
        try {
            preparedStatement.setLong(1, transfer.getFromBankAccountId());
            preparedStatement.setLong(2, transfer.getToBankAccountId());
            preparedStatement.setBigDecimal(3, transfer.getAmount());
            preparedStatement.setInt(4, transfer.getCurrency().getId());
            preparedStatement.setInt(5, transfer.getStatus().getId());
            preparedStatement.setString(6, transfer.getFailMessage());
            preparedStatement.setDate(7, new java.sql.Date(transfer.getCreationDate().getTime()));
            preparedStatement.setDate(8, new java.sql.Date(transfer.getUpdateDate().getTime()));
        } catch (SQLException e) {
            log.error("Transfers prepared statement could not be initialized by values", e);
        }

    }

    private Transfer extractTransactionFromResultSet(ResultSet transfersRS) throws SQLException {
        Transfer transfer = new Transfer();
        transfer.setId(transfersRS.getLong(TRANSFER_ID_ROW));
        transfer.setFromBankAccountId(transfersRS.getLong(TRANSFER_FROM_ACCOUNT_ROW));
        transfer.setToBankAccountId(transfersRS.getLong(TRANSFER_TO_ACCOUNT_ROW));
        transfer.setAmount(transfersRS.getBigDecimal(TRANSFER_AMOUNT_ROW));
        transfer.setCurrency(Currency.valueOf(transfersRS.getInt(TRANSFER_CURRENCY_ROW)));
        transfer.setStatus(TransferStatus.valueOf(transfersRS.getInt(TRANSFER_STATUS_ROW)));
        transfer.setFailMessage(transfersRS.getString(TRANSFER_STATUS_ROW));
        transfer.setCreationDate(transfersRS.getDate(TRANSFER_CREATION_DATE_ROW));
        transfer.setUpdateDate(transfersRS.getDate(TRANSFER_UPDATE_DATE_ROW));
        return transfer;
    }
}
