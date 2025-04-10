package com.gym.vending.simulator;

import com.gym.vending.app.CashAccount;
import com.gym.vending.app.CustomerSession;
import com.gym.vending.pages.ProteinBar;
import com.gym.vending.pages.EnergyDrink;
import com.gym.vending.pages.Towel;
import com.gym.vending.pages.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class RandomScenarioGenerator {
    private static final Logger logger = LoggerFactory.getLogger(RandomScenarioGenerator.class);
    private static final Random random = new Random();

    public static TestScenario generateRandomScenario() {
        // Generate random balance between 5 and 50 euros
        double balance = 5 + random.nextInt(46);

        // Generate random quantities
        int proteinBars = random.nextInt(5);
        int energyDrinks = random.nextInt(5);
        int towels = random.nextInt(5);

        // Create customer session
        CashAccount cashAccount = new CashAccount(balance);
        cashAccount.setInitialBalance(balance); // 👈 this is the fix
        CustomerSession session = new CustomerSession(cashAccount);


        double totalCost = 0.0;

        // Add Protein Bars
        if (proteinBars > 0) {
            ProteinBar bar = new ProteinBar(proteinBars);
            session.addToCart(bar);
            totalCost += bar.getPrice() * proteinBars;
        }

        // Add Energy Drinks
        if (energyDrinks > 0) {
            EnergyDrink drink = new EnergyDrink(energyDrinks);
            session.addToCart(drink);
            totalCost += drink.getPrice() * energyDrinks;
        }

        // Add Towels
        if (towels > 0) {
            Towel towel = new Towel(towels);
            session.addToCart(towel);
            totalCost += towel.getPrice() * towels;
        }

        // Determine available payment methods
        List<String> availableMethods = new ArrayList<>();
        availableMethods.add("Debit/Credit Card");
        availableMethods.add("Apple Pay");
        availableMethods.add("Google Pay");

        if (balance >= totalCost) {
            availableMethods.add("Cash Account");
        }

        return new TestScenario(balance, totalCost, session, availableMethods);
    }
}
