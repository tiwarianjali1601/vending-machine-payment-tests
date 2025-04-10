package com.gym.vending.pages;

public class Towel implements Item{
    private final double price = 5.20; //fixed price
    private final String name = "Towel"; // Proper display name
    private int qty;

    public Towel( int qty) {
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
