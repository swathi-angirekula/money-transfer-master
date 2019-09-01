package com.mycompany.revolutmoneytransfer.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class Transfer implements ModelHasId{
    private Long id;
    private Long fromBankAccountId;
    private Long toBankAccountId;
    private BigDecimal amount;
    private Currency currency;
    private Date creationDate;
    private Date updateDate;
    private TransferStatus status;
    private String failMessage;

    public Transfer() {
        this.creationDate = new Date();
        this.updateDate = new Date();
        this.status = TransferStatus.INITIATED;
        this.failMessage = "";
    }

    public Transfer(Long fromBankAccountId, Long toBankAccountId, BigDecimal amount) {
        this();
        this.fromBankAccountId = fromBankAccountId;
        this.toBankAccountId = toBankAccountId;
        this.amount = amount;
        this.currency = Currency.GBP;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFromBankAccountId() {
        return fromBankAccountId;
    }

    public void setFromBankAccountId(Long fromBankAccountId) {
        this.fromBankAccountId = fromBankAccountId;
    }

    public Long getToBankAccountId() {
        return toBankAccountId;
    }

    public void setToBankAccountId(Long toBankAccountId) {
        this.toBankAccountId = toBankAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public TransferStatus getStatus() {
        return status;
    }

    public void setStatus(TransferStatus status) {
        this.status = status;
    }

    public String getFailMessage() {
        return failMessage;
    }

    public void setFailMessage(String failMessage) {
        this.failMessage = failMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transfer that = (Transfer) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
