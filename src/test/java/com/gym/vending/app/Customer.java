package com.gym.vending.app;

public class Customer {
    private int cashAccount;
    private boolean loggedIn;

    public Customer(int cashAccount) {
        this.cashAccount = cashAccount;
        this.loggedIn = true; // Simulating a successful login
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public int getCashAccount() {
        return cashAccount;
    }

    public void deduct(int amount) {
        this.cashAccount -= amount;
    }
}
