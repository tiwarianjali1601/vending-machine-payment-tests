package com.gym.vending.app;

import com.gym.vending.pages.Cart;
import com.gym.vending.pages.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CustomerSession {
    private static final Logger logger = LoggerFactory.getLogger(CustomerSession.class);
    private final CashAccount cashAccount;
    private final Cart cart; // ✅ now it's a Cart object

    public CustomerSession(CashAccount cashAccount) {
        this.cashAccount = cashAccount;
        this.cart = new Cart(); // ✅ use Cart class
        logger.info("🚀 New CustomerSession started with balance: {}€", cashAccount.getBalance());
    }

    public void addToCart(Item item) {
        cart.addToCart(item);  // ✅ use Cart's method
        logger.info("🛒 Adding item to cart: {} ({}€ x {})", item.getName(), item.getPrice(), item.getQty());
    }

    public void clearCart() {
        cart.getItems().clear();  // or implement clear in Cart class
    }

    public CashAccount getCashAccount() {
        return cashAccount;
    }

    public double getCartTotal() {
        return cart.getTotal();
    }

    public Cart getCart() {
        return cart;
    }

    public List<Item> getCartItems() {
        return cart.getItems();
    }
}
