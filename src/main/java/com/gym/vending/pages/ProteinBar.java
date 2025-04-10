package com.gym.vending.pages;

public class ProteinBar implements Item {
    private final double price = 15.12; //fixed price
    private final String name = "Protein Bar"; // Proper display name
    private int qty;
    public ProteinBar( int qty) {
        this.qty = qty;
    }
    @Override
    public double getPrice() {
        return price;
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public int getQty() {
        return qty;
    }
    @Override
    public String toString() {
        return name + " - " + price + "€ x " + qty;
    }
}
