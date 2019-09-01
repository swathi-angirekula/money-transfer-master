package com.mycompany.revolutmoneytransfer.service;

import com.mycompany.revolutmoneytransfer.dto.BankAccountDto;
import com.mycompany.revolutmoneytransfer.dto.TransferDto;
import com.mycompany.revolutmoneytransfer.exceptions.ObjectModificationException;
import com.mycompany.revolutmoneytransfer.model.Currency;
import com.mycompany.revolutmoneytransfer.model.Transfer;
import com.mycompany.revolutmoneytransfer.model.TransferStatus;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;
import static org.testng.AssertJUnit.assertArrayEquals;

public class TransfersServiceTest {

    private static final TransfersService staticTransferService = TransfersService.getInstance();

    @Test
    public void testAllTransfersRetrieval() {
        TransferDto transferDto = mock(TransferDto.class);
        TransfersService transfersService = new TransfersService(transferDto);

        Collection<Transfer> testList = Arrays.asList(new Transfer(
                        BankAccountDto.SA_BANK_ACCOUNT_ID,
                        BankAccountDto.ES_BANK_ACCOUNT_ID,
                        BigDecimal.ZERO),
                new Transfer(
                        BankAccountDto.ES_BANK_ACCOUNT_ID,
                        BankAccountDto.SIVAIAH_BANK_ACCOUNT_ID,
                        BigDecimal.ZERO)
        );

        when(transferDto.getAllTransfers()).thenReturn(testList);

        Collection<Transfer> transfers = transfersService.getAllTransfers();

        assertNotNull(transfers);
        assertArrayEquals(testList.toArray(), transfers.toArray());
    }


    @Test(expectedExceptions = ObjectModificationException.class)
    public void testCreateTransactionWithNullFrom() throws ObjectModificationException {
        staticTransferService.createTransactionToTransferMoney(new Transfer(
                null, 2L, BigDecimal.TEN));
    }


    @Test(expectedExceptions = ObjectModificationException.class)
    public void testCreateTransactionWithNullTo() throws ObjectModificationException {
        staticTransferService.createTransactionToTransferMoney(new Transfer(
                1L, null, BigDecimal.TEN
        ));
    }


    @Test(expectedExceptions = ObjectModificationException.class)
    public void testCreateTransactionWithSameAccounts() throws ObjectModificationException {
        staticTransferService.createTransactionToTransferMoney(new Transfer(
                BankAccountDto.SA_BANK_ACCOUNT_ID,
                BankAccountDto.SA_BANK_ACCOUNT_ID,
                BigDecimal.TEN
        ));
    }


    @Test(expectedExceptions = ObjectModificationException.class)
    public void testCreateTransactionWithZeroAmount() throws ObjectModificationException {
        staticTransferService.createTransactionToTransferMoney(new Transfer(
                BankAccountDto.SA_BANK_ACCOUNT_ID,
                BankAccountDto.ES_BANK_ACCOUNT_ID,
                BigDecimal.ZERO
        ));
    }


    @Test
    public void testCreateTransaction() throws ObjectModificationException {
        Long TRANSFER_ID = 123L;

        TransferDto transferDto = mock(TransferDto.class);

        Transfer transfer = new Transfer(
                BankAccountDto.SA_BANK_ACCOUNT_ID,
                BankAccountDto.ES_BANK_ACCOUNT_ID,
                BigDecimal.TEN
        );
        transfer.setId(TRANSFER_ID);

        when(transferDto.createTransaction(any())).thenReturn(transfer);

        when(transferDto.getAllTransactionIdsByStatus(any())).thenReturn(
                Collections.singletonList(transfer.getId())
        );

        doAnswer(invocation -> {
            transfer.setStatus(TransferStatus.SUCCESSFUL);
            return null;
        }).when(transferDto).executeTransaction(anyLong());

        TransfersService transfersService = new TransfersService(transferDto);
        Transfer createdTransaction = transfersService.createTransactionToTransferMoney(transfer);

        assertEquals(createdTransaction, transfer);
        assertEquals(createdTransaction.getStatus(), TransferStatus.INITIATED);

        transfersService.executeTransfers();

        assertEquals(transfer.getStatus(), TransferStatus.SUCCESSFUL);
    }
}
