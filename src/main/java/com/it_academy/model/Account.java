package com.it_academy.model;

import java.util.Objects;

public class Account {
    Integer accountId;
    Integer userId;
    Integer balance;
    Currency currency;

    public Integer getAccountId() {
        return accountId;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getBalance() {
        return balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public void setUserId(Integer userId) {
        this.userId = User.userId;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", userId=" + userId +
                ", balance=" + balance +
                ", currency=" + currency +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return accountId.equals(account.accountId) && userId.equals(account.userId) && balance.equals(account.balance) && currency == account.currency;
    }
}
