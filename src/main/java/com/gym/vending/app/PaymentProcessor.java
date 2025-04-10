package com.gym.vending.app;

import com.gym.vending.pages.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PaymentProcessor {
    private static final Logger logger = LoggerFactory.getLogger(PaymentProcessor.class);
    private static final List<String> ALL_PAYMENT_METHODS = Arrays.asList(
            "Debit/Credit Card",
            "Apple Pay",
            "Google Pay",
            "Cash Account"
    );

    // Return list of valid payment methods depending on user's balance
    public List<String> getAvailablePaymentMethods(CustomerSession session) {
        List<String> availableMethods = new ArrayList<>(ALL_PAYMENT_METHODS);

        double total = session.getCartTotal();
        double balance = session.getCashAccount().getBalance();

        if (total > balance) {
            availableMethods.remove("Cash Account");
        }

        logger.info("Available payment methods: {}", availableMethods);
        return availableMethods;
    }

    // Attempt to process payment with selected method
    public PaymentResult processPayment(CustomerSession session, String paymentMethod) {
        long startTime = System.currentTimeMillis();

        // Log items in the cart for reference
        List<Item> items = session.getCartItems();
        logger.info("🧾 Cart contains:");
        for (Item item : items) {
            logger.info("➡️ {} ({}€ x {})", item.getName(), item.getPrice(), item.getQty());
        }

        try {
            double cartTotal = session.getCartTotal();
            double balance = session.getCashAccount().getBalance();

            if ("Cash Account".equalsIgnoreCase(paymentMethod)) {
                if (balance >= cartTotal) {
                    session.getCashAccount().deduct(cartTotal);
                    session.clearCart();
                    return new PaymentResult(true, "Success", System.currentTimeMillis() - startTime);
                } else {
                    return new PaymentResult(false, "Insufficient funds", System.currentTimeMillis() - startTime);
                }
            } else {
                // Assume all other payments succeed
                session.clearCart();
                return new PaymentResult(true, "Success", System.currentTimeMillis() - startTime);
            }
        } catch (Exception e) {
            logger.error("Payment processing failed: {}", e.getMessage());
            return new PaymentResult(false, "Payment failed: " + e.getMessage(), System.currentTimeMillis() - startTime);
        }
    }
}
