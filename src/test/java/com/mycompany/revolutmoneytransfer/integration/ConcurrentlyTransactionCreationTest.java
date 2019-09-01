package com.mycompany.revolutmoneytransfer.integration;

import com.mycompany.revolutmoneytransfer.exceptions.ObjectModificationException;
import com.mycompany.revolutmoneytransfer.model.BankAccount;
import com.mycompany.revolutmoneytransfer.model.Currency;
import com.mycompany.revolutmoneytransfer.model.Transfer;
import com.mycompany.revolutmoneytransfer.service.BankAccountService;
import com.mycompany.revolutmoneytransfer.service.TransfersService;
import org.hamcrest.Matchers;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;

public class ConcurrentlyTransactionCreationTest {
    private TransfersService transfersService = TransfersService.getInstance();
    private BankAccountService bankAccountService = BankAccountService.getInstance();

    private static final BigDecimal INITIAL_BALANCE = BigDecimal.valueOf(1000L);
    private static final BigDecimal TRANSFER_AMOUNT = BigDecimal.ONE;
    private static final int INVOCATION_COUNT = 1000;

    private Long fromBankAccountId;
    private Long toBankAccountId;

    @BeforeClass
    public void initData() throws ObjectModificationException {
        BankAccount fromBankAccount = new BankAccount(
                "New Bank Account",
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
        Transfer transfer = new Transfer(
                fromBankAccountId,
                toBankAccountId,
                TRANSFER_AMOUNT
        );

        transfersService.createTransactionToTransferMoney(transfer);
    }

    @AfterClass
    public void checkResults() {
        BankAccount bankAccount = bankAccountService.getBankAccountById(fromBankAccountId);

        assertThat(bankAccount.getBalance(), Matchers.comparesEqualTo(INITIAL_BALANCE));
        assertThat(bankAccount.getBlockedAmount(),
                Matchers.comparesEqualTo(
                        BigDecimal.ZERO.add(
                            TRANSFER_AMOUNT.multiply(BigDecimal.valueOf(INVOCATION_COUNT)))
                )
        );
    }
}
