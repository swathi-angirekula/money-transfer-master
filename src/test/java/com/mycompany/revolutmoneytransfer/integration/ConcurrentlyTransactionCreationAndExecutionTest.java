package com.mycompany.revolutmoneytransfer.integration;

import com.mycompany.revolutmoneytransfer.exceptions.ObjectModificationException;
import com.mycompany.revolutmoneytransfer.model.BankAccount;
import com.mycompany.revolutmoneytransfer.model.Transfer;
import com.mycompany.revolutmoneytransfer.service.BankAccountService;
import com.mycompany.revolutmoneytransfer.service.TransfersService;
import org.hamcrest.Matchers;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.MatcherAssert.assertThat;

public class ConcurrentlyTransactionCreationAndExecutionTest {
    private TransfersService transfersService = TransfersService.getInstance();
    private BankAccountService bankAccountService = BankAccountService.getInstance();

    private static final BigDecimal INITIAL_BALANCE = BigDecimal.valueOf(1000L);
    private static final BigDecimal TRANSFER_AMOUNT = BigDecimal.ONE;
    private static final int INVOCATION_COUNT = 1000;

    private Long fromBankAccountId;
    private Long toBankAccountId;
    private AtomicInteger invocationsDone = new AtomicInteger(0);

    @BeforeClass
    public void initData() throws ObjectModificationException {
        BankAccount fromBankAccount = new BankAccount(
                "New Bank Account 1",
                INITIAL_BALANCE,
                BigDecimal.ZERO
        );

        BankAccount toBankAccount = new BankAccount(
                "New Bank Account 2",
                BigDecimal.ZERO,
                BigDecimal.ZERO
        );

        fromBankAccountId = bankAccountService.createBankAccount(fromBankAccount).getId();
        toBankAccountId = bankAccountService.createBankAccount(toBankAccount).getId();
    }

    @Test(threadPoolSize = 100, invocationCount = INVOCATION_COUNT)
    public void testConcurrentTransactionCreation() throws ObjectModificationException {
        int currentTestNumber = invocationsDone.addAndGet(1);

        Transfer transfer = new Transfer(
                fromBankAccountId,
                toBankAccountId,
                TRANSFER_AMOUNT
        );

        transfersService.createTransactionToTransferMoney(transfer);

        if (currentTestNumber % 5 == 0) {
            transfersService.executeTransfers();
        }
    }

    @AfterClass
    public void checkResults() {
        transfersService.executeTransfers();
        BankAccount fromBankAccount = bankAccountService.getBankAccountById(fromBankAccountId);
        assertThat(fromBankAccount.getBalance(),
                Matchers.comparesEqualTo(
                        INITIAL_BALANCE.subtract(
                                TRANSFER_AMOUNT.multiply(BigDecimal.valueOf(INVOCATION_COUNT)))
                )
        );
        assertThat(fromBankAccount.getBlockedAmount(), Matchers.comparesEqualTo(BigDecimal.ZERO));
    }
}
