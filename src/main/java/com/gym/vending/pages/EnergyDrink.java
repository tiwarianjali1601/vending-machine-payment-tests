package com.gym.vending.pages;

public class EnergyDrink implements Item {
    private final double price = 10.23; //fixed price
    private final String name = "Energy Drink"; // Proper display name
    private int qty;

    public EnergyDrink( int qty) {
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
