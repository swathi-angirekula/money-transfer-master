package com.mycompany.revolutmoneytransfer.dto;

import com.mycompany.revolutmoneytransfer.db.DbUtils;
import com.mycompany.revolutmoneytransfer.exceptions.ObjectModificationException;
import com.mycompany.revolutmoneytransfer.model.BankAccount;
import com.mycompany.revolutmoneytransfer.model.Currency;
import com.mycompany.revolutmoneytransfer.model.ExceptionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;


public class BankAccountDto {
    private static final String BANK_ACCOUNT_TABLE_NAME = "bank_account";
    private static final String BANK_ACCOUNT_ID_ROW = "id";
    private static final String BANK_ACCOUNT_OWNER_NAME_ROW = "owner_name";
    private static final String BANK_ACCOUNT_BALANCE_ROW = "balance";
    private static final String BANK_ACCOUNT_BLOCKED_AMOUNT_ROW = "blocked_amount";
    private static final String BANK_ACCOUNT_CURRENCY_ID_ROW = "currency_id";

    public static final Long SA_BANK_ACCOUNT_ID = 1L;
    public static final Long ES_BANK_ACCOUNT_ID = 2L;
    public static final Long SIVAIAH_BANK_ACCOUNT_ID = 3L;

    private static final Logger log = LoggerFactory.getLogger(BankAccountDto.class);

    private static final BankAccountDto bas = new BankAccountDto();
    private DbUtils dbUtils = DbUtils.getInstance();

    private BankAccountDto() {
    }

    public static BankAccountDto getInstance() {
        return bas;
    }


    public Collection<BankAccount> getAllBankAccounts() {
        return dbUtils.executeQuery("select * from " + BANK_ACCOUNT_TABLE_NAME, getBankAccounts -> {
            Collection<BankAccount> bankAccounts = new ArrayList<>();

            try (ResultSet bankAccountsRS = getBankAccounts.executeQuery()) {
                if (bankAccountsRS != null) {
                    while (bankAccountsRS.next()) {
                        bankAccounts.add(extractBankAccountFromResultSet(bankAccountsRS));
                    }
                }
            }

            return bankAccounts;
        }).getResult();
    }


    public BankAccount getBankAccountById(Long id) {
        String GET_BANK_ACCOUNT_BY_ID_SQL =
                "select * from " + BANK_ACCOUNT_TABLE_NAME + " ba " +
                        "where ba." + BANK_ACCOUNT_ID_ROW + " = ?";

        return dbUtils.executeQuery(GET_BANK_ACCOUNT_BY_ID_SQL, getBankAccount -> {
            getBankAccount.setLong(1, id);
            try (ResultSet bankAccountRS = getBankAccount.executeQuery()) {
                if (bankAccountRS != null && bankAccountRS.first()) {
                    return extractBankAccountFromResultSet(bankAccountRS);
                }
            }

            return null;
        }).getResult();
    }


    BankAccount getForUpdateBankAccountById(Connection con, Long id) {
        String GET_BANK_ACCOUNT_BY_ID_SQL =
                "select * from " + BANK_ACCOUNT_TABLE_NAME + " ba " +
                        "where ba." + BANK_ACCOUNT_ID_ROW + " = ? " +
                        "for update";

        return dbUtils.executeQueryInConnection(con, GET_BANK_ACCOUNT_BY_ID_SQL, getBankAccount -> {
            getBankAccount.setLong(1, id);
            try (ResultSet bankAccountRS = getBankAccount.executeQuery()) {
                if (bankAccountRS != null && bankAccountRS.first()) {
                    return extractBankAccountFromResultSet(bankAccountRS);
                }
            }

            return null;
        }).getResult();
    }


    public void updateBankAccountSafe(BankAccount bankAccount) throws ObjectModificationException {
        String UPDATE_BANK_ACCOUNT_SQL =
                "update " + BANK_ACCOUNT_TABLE_NAME +
                        " set " +
                        BANK_ACCOUNT_OWNER_NAME_ROW + " = ? " +
                        "where " + BANK_ACCOUNT_ID_ROW + " = ?";

        if (bankAccount.getId() == null || bankAccount.getName() == null) {
            throw new ObjectModificationException(ExceptionType.OBJECT_IS_MALFORMED, "Id and Name fields could not be NULL");
        }

        DbUtils.QueryExecutor<Integer> queryExecutor = updateBankAccount -> {
            updateBankAccount.setString(1, bankAccount.getName());
            updateBankAccount.setLong(2, bankAccount.getId());

            return updateBankAccount.executeUpdate();
        };

        int result = dbUtils.executeQuery(UPDATE_BANK_ACCOUNT_SQL, queryExecutor).getResult();

        if (result == 0) {
            throw new ObjectModificationException(ExceptionType.OBJECT_IS_NOT_FOUND);
        }
    }


    public void depositMoneyIntoBankAccountSafe(BankAccount bankAccount) throws ObjectModificationException {
        String UPDATE_BANK_ACCOUNT_SQL =
                "update " + BANK_ACCOUNT_TABLE_NAME +
                        " set " +
                        BANK_ACCOUNT_BALANCE_ROW + " = ? " +
                        "where " + BANK_ACCOUNT_ID_ROW + " = ?";

        if (bankAccount.getId() == null || bankAccount.getName() == null) {
            throw new ObjectModificationException(ExceptionType.OBJECT_IS_MALFORMED, "Id and Name fields could not be NULL");
        }

        DbUtils.QueryExecutor<Integer> queryExecutor = updateBankAccount -> {
            updateBankAccount.setBigDecimal(1, bankAccount.getBalance());
            updateBankAccount.setLong(2, bankAccount.getId());

            return updateBankAccount.executeUpdate();
        };

        int result = dbUtils.executeQuery(UPDATE_BANK_ACCOUNT_SQL, queryExecutor).getResult();

        if (result == 0) {
            throw new ObjectModificationException(ExceptionType.OBJECT_IS_NOT_FOUND);
        }
    }    

    void updateBankAccount(BankAccount bankAccount, Connection con) throws ObjectModificationException {
        String UPDATE_BANK_ACCOUNT_SQL =
                "update " + BANK_ACCOUNT_TABLE_NAME +
                        " set " +
                        BANK_ACCOUNT_OWNER_NAME_ROW + " = ?, " +
                        BANK_ACCOUNT_BALANCE_ROW + " = ?, " +
                        BANK_ACCOUNT_BLOCKED_AMOUNT_ROW + " = ? " +
                        "where " + BANK_ACCOUNT_ID_ROW + " = ?";

        verify(bankAccount);

        DbUtils.QueryExecutor<Integer> queryExecutor = updateBankAccount -> {
            fillInPreparedStatement(updateBankAccount, bankAccount);
            updateBankAccount.setLong(4, bankAccount.getId());

            return updateBankAccount.executeUpdate();
        };

        int result;
        if (con == null) {
            result = dbUtils.executeQuery(UPDATE_BANK_ACCOUNT_SQL, queryExecutor).getResult();
        } else {
            result = dbUtils.executeQueryInConnection(con, UPDATE_BANK_ACCOUNT_SQL, queryExecutor).getResult();
        }

        if (result == 0) {
            throw new ObjectModificationException(ExceptionType.OBJECT_IS_NOT_FOUND);
        }
    }

    public BankAccount createBankAccount(BankAccount bankAccount) throws ObjectModificationException {
        String INSERT_BANK_ACCOUNT_SQL =
                "insert into " + BANK_ACCOUNT_TABLE_NAME +
                        " (" +
                        BANK_ACCOUNT_OWNER_NAME_ROW + ", " +
                        BANK_ACCOUNT_BALANCE_ROW + ", " +
                        BANK_ACCOUNT_BLOCKED_AMOUNT_ROW + ", " +
                        BANK_ACCOUNT_CURRENCY_ID_ROW +
                        ") values (?, ?, ?, ?)";

        verify(bankAccount);

        bankAccount = dbUtils.executeQuery(INSERT_BANK_ACCOUNT_SQL,
                new DbUtils.CreationQueryExecutor<>(bankAccount, BankAccountDto::fillInPreparedStatement)).getResult();

        if (bankAccount == null) {
            throw new ObjectModificationException(ExceptionType.COULD_NOT_OBTAIN_ID);
        }

        return bankAccount;
    }

    private BankAccount extractBankAccountFromResultSet(ResultSet bankAccountsRS) throws SQLException {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(bankAccountsRS.getLong(BANK_ACCOUNT_ID_ROW));
        bankAccount.setName(bankAccountsRS.getString(BANK_ACCOUNT_OWNER_NAME_ROW));
        bankAccount.setBalance(bankAccountsRS.getBigDecimal(BANK_ACCOUNT_BALANCE_ROW));
        bankAccount.setBlockedAmount(bankAccountsRS.getBigDecimal(BANK_ACCOUNT_BLOCKED_AMOUNT_ROW));
        bankAccount.setCurrency(Currency.valueOf(bankAccountsRS.getInt(BANK_ACCOUNT_CURRENCY_ID_ROW)));

        return bankAccount;
    }

 
    private void verify(BankAccount bankAccount) throws ObjectModificationException {
        if (bankAccount.getId() == null) {
            throw new ObjectModificationException(ExceptionType.OBJECT_IS_MALFORMED,
                    "ID value is invalid");
        }

        if (bankAccount.getName() == null || bankAccount.getBalance() == null ||
                bankAccount.getBlockedAmount() == null || bankAccount.getCurrency() == null) {
            throw new ObjectModificationException(ExceptionType.OBJECT_IS_MALFORMED, "Fields could not be NULL");
        }
    }


    private static void fillInPreparedStatement(PreparedStatement preparedStatement, BankAccount bankAccount) {
        try {
            preparedStatement.setString(1, bankAccount.getName());
            preparedStatement.setBigDecimal(2, bankAccount.getBalance());
            preparedStatement.setBigDecimal(3, bankAccount.getBlockedAmount());
            preparedStatement.setLong(4, bankAccount.getCurrency().getId());
        } catch (SQLException e) {
            log.error("BankAccount prepared statement could not be initialized by values", e);
        }
    }
}
