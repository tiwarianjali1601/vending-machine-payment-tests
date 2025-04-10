package com.gym.vending.pages;

import java.util.ArrayList;
import java.util.List;

/**
 * Cart class that holds multiple item types (ProteinBar, EnergyDrink, Towel etc.)
 */
public class Cart {
    private final List<Item> items = new ArrayList<>();

    /**
     * Add an item to the cart
     */
    public void addToCart(Item item) {
        items.add(item);
    }
    /**
     * Get all items in the cart
     */
    public List<Item> getItems() {
        return items;
    }
    /**
     * Calculate the total price of all items in the cart
     */
    public double getTotal() {
        double total = 0.0;
        for (Item item : items) {
            total += item.getPrice() * item.getQty();
        }
        return total;
    }
    /**
     * Print cart details for debug/logging
     */
    public void showCart() {
        for (Item item : items) {
            System.out.println(item);
        }
        System.out.println("Total: " + getTotal() + "€");
    }

    /**
     * Optional: Get total quantity of items
     */
    public int getTotalItemCount() {
        int count = 0;
        for (Item item : items) {
            count += item.getQty();
        }
        return count;
    }
}
