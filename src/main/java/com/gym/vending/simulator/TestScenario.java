package com.gym.vending.simulator;

import com.gym.vending.app.CustomerSession;
import com.gym.vending.pages.Item;

import java.util.List;

public class TestScenario {
    private final double initialBalance;
    private final double totalCost;
    private final CustomerSession session;
    private final List<String> availablePaymentMethods;

    public TestScenario(double initialBalance, double totalCost, CustomerSession session, List<String> availablePaymentMethods) {
        this.initialBalance = initialBalance;
        this.totalCost = totalCost;
        this.session = session;
        this.availablePaymentMethods = availablePaymentMethods;
    }

    public double getInitialBalance() {
        return initialBalance;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public CustomerSession getSession() {
        return session;
    }

    public List<String> getAvailablePaymentMethods() {
        return availablePaymentMethods;
    }

    public boolean isExpectedToSucceed() {
        return initialBalance >= totalCost;
    }

    // 🔍 Optional: For debugging - get all items in the cart
    public List<Item> getCartItems() {
        return session.getCart().getItems();
    }

    @Override
    public String toString() {
        return "TestScenario{" +
                "initialBalance=" + initialBalance +
                ", totalCost=" + totalCost +
                ", availablePaymentMethods=" + availablePaymentMethods +
                ", cartItems=" + getCartItems() +
                '}';
    }
}
