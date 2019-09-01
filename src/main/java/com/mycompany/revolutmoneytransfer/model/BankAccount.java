package com.mycompany.revolutmoneytransfer.model;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Random;

public class BankAccount implements ModelHasId {
    private Long id;
    private String name;
    private BigDecimal balance;
    private BigDecimal blockedAmount;
    private Currency currency;

    public BankAccount() {
    }

    public BankAccount(String name, BigDecimal balance, BigDecimal blockedAmount) {
        this(new Random().nextLong(), name, balance, blockedAmount, Currency.GBP);
    }

    public BankAccount(Long id, String name, BigDecimal balance, BigDecimal blockedAmount, Currency currency) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.blockedAmount = blockedAmount;
        this.currency = currency;
    }

    public BankAccount(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getBlockedAmount() {
        return blockedAmount;
    }

    public void setBlockedAmount(BigDecimal blockedAmount) {
        this.blockedAmount = blockedAmount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankAccount that = (BankAccount) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
