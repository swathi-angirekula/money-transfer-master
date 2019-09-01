package com.mycompany.revolutmoneytransfer.service;

import com.mycompany.revolutmoneytransfer.dto.BankAccountDto;
import com.mycompany.revolutmoneytransfer.exceptions.ObjectModificationException;
import com.mycompany.revolutmoneytransfer.model.BankAccount;

import java.util.Collection;

public class BankAccountService {
    private static final BankAccountService bas = new BankAccountService();

    public static BankAccountService getInstance() {
        return bas;
    }

    public Collection<BankAccount> getAllBankAccounts() {
        return BankAccountDto.getInstance().getAllBankAccounts();
    }

    public BankAccount getBankAccountById(Long id) {
        return BankAccountDto.getInstance().getBankAccountById(id);
    }

    public void updateBankAccount(BankAccount bankAccount) throws ObjectModificationException {
        BankAccountDto.getInstance().updateBankAccountSafe(bankAccount);
    }

    public void depositMoneyIntoBankAccount(BankAccount bankAccount) throws ObjectModificationException {
        BankAccountDto.getInstance().depositMoneyIntoBankAccountSafe(bankAccount);
    }    
    public BankAccount createBankAccount(BankAccount bankAccount) throws ObjectModificationException {
        return BankAccountDto.getInstance().createBankAccount(bankAccount);
    }
    
}
