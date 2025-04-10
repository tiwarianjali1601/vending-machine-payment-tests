package com.gym.vending.app;

import java.util.Arrays;
import java.util.List;

public class VendingMachine {
    private int cartTotal;
    private String message;

    public void selectItems(int total) {
        this.cartTotal = total;
    }

    public boolean processPayment(Customer customer) {
        if (customer.getCashAccount() >= cartTotal) {
            customer.deduct(cartTotal);
            message = "Success";
            return true;
        } else {
            message = "Insufficient funds";
            return false;
        }
    }

    public String getMessage() {
        return message;
    }

    public List<String> getAvailablePaymentMethods(Customer customer) {
        List<String> methods = Arrays.asList("Debit/Credit Card", "Apple Pay", "Google Pay");
        if (customer.getCashAccount() >= cartTotal) {
            methods = Arrays.asList("Debit/Credit Card", "Apple Pay", "Google Pay", "Cash Account");
        }
        return methods;
    }
}
