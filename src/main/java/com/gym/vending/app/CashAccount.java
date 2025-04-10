package com.gym.vending.app;

public class CashAccount {
    private double balance;
    private double initialBalance;
    public CashAccount(double balance) {
        this.balance = balance;
        this.initialBalance = balance; // Track initial value
    }
    public double getBalance() {
        return balance;
    }
    public double getInitialBalance() {
        return initialBalance;
    }
    public void deduct(double amount) {
        this.balance -= amount;
    }

    // Optional: In case you ever want to reset initialBalance manually
    public void setInitialBalance(double initialBalance) {
        this.initialBalance = initialBalance;
    }
}
