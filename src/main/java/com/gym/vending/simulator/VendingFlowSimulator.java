package com.gym.vending.simulator;

import com.gym.vending.app.CustomerSession;
import com.gym.vending.pages.EnergyDrink;
import com.gym.vending.pages.ProteinBar;
import com.gym.vending.pages.Towel;
import com.gym.vending.app.CashAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VendingFlowSimulator {

    private static final Logger logger = LoggerFactory.getLogger(VendingFlowSimulator.class);

    public static void main(String[] args) {
        logger.info("🎬 Starting Vending Flow Simulations");

        // Scenario 1: Successful purchase
        logger.info("\n➡️  Scenario 1: Balance = 15€, Purchase = ProteinBar + EnergyDrink");
        CashAccount cashAccount1 = new CashAccount(15.0);
        CustomerSession session1 = new CustomerSession(cashAccount1);
        session1.addToCart(new ProteinBar(1));
        session1.addToCart(new EnergyDrink(1));

        // Scenario 2: Failed purchase due to insufficient funds
        logger.info("\n➡️  Scenario 2: Balance = 12€, Purchase = ProteinBar + Towel");
        CashAccount cashAccount2 = new CashAccount(12.0);
        CustomerSession session2 = new CustomerSession(cashAccount2);
        session2.addToCart(new ProteinBar(1));
        session2.addToCart(new Towel(1));

        // Scenario 3: Exact funds
        logger.info("\n➡️  Scenario 3: Balance = 10€, Purchase = EnergyDrink");
        CashAccount cashAccount3 = new CashAccount(10.0);
        CustomerSession session3 = new CustomerSession(cashAccount3);
        session3.addToCart(new EnergyDrink(1));

        logger.info("🏁 Simulation Complete");
    }
}
