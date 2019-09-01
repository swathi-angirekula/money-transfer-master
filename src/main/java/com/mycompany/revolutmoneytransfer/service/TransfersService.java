package com.mycompany.revolutmoneytransfer.service;

import com.mycompany.revolutmoneytransfer.dto.TransferDto;
import com.mycompany.revolutmoneytransfer.exceptions.ObjectModificationException;
import com.mycompany.revolutmoneytransfer.model.Currency;
import com.mycompany.revolutmoneytransfer.model.ExceptionType;
import com.mycompany.revolutmoneytransfer.model.Transfer;
import com.mycompany.revolutmoneytransfer.model.TransferStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TransfersService {
    private static final Logger log = LoggerFactory.getLogger(TransfersService.class);

    private static TransfersService ts;
    private TransferDto transferDto;
    private static ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    TransfersService(TransferDto transferDto) {
        this.transferDto = transferDto;
        executorService.scheduleAtFixedRate(() ->
                        ts.executeTransfers(),
                0, 5, TimeUnit.SECONDS);
        log.info("Transaction Executor initiated");
    }

    public static TransfersService getInstance() {
        if(ts == null){
            synchronized (TransfersService.class) {
                if(ts == null){
                    ts = new TransfersService(TransferDto.getInstance());
                }
            }
        }
        return ts;
    }

    public Collection<Transfer> getAllTransfers() {
        return transferDto.getAllTransfers();
    }

    private Collection<Long> getAllTransactionIdsByStatus(TransferStatus transferStatus) {
        return transferDto.getAllTransactionIdsByStatus(transferStatus);
    }

    public Transfer getTransactionById(Long id) {
        return transferDto.getTransactionById(id);
    }


    public Transfer createTransactionToTransferMoney(Transfer transfer) throws ObjectModificationException {
        if (transfer.getFromBankAccountId() == null || transfer.getToBankAccountId() == null) {
            throw new ObjectModificationException(ExceptionType.OBJECT_IS_MALFORMED,
                    "The transfer has not provided from Bank Account or to Bank Account values");
        }
        if (transfer.getFromBankAccountId().equals(transfer.getToBankAccountId())) {
            throw new ObjectModificationException(ExceptionType.OBJECT_IS_MALFORMED,
                    "The sender and recipient should not be same");
        }
        if (transfer.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ObjectModificationException(ExceptionType.OBJECT_IS_MALFORMED,
                    "The amount should be more than 0");
        }
        transfer.setCurrency(Currency.GBP);
        return transferDto.createTransaction(transfer);
    }

    public void executeTransfers() {
        log.info("Starting of Transaction executor");
        Collection<Long> initiatedTransactionIds = getAllTransactionIdsByStatus(TransferStatus.INITIATED);

        for (Long transferId : initiatedTransactionIds) {
            try {
                transferDto.executeTransaction(transferId);
            } catch (ObjectModificationException e) {
                log.error("Could not execute transfer with id :" + transferId, e);
            }
        }
        log.info("Transaction executor ended");
    }
}
