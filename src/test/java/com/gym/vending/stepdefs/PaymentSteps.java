package com.gym.vending.stepdefs;

import com.gym.vending.app.Customer;
import com.gym.vending.app.VendingMachine;
import io.cucumber.java.en.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.Assert.*;

public class PaymentSteps {

    private static final Logger logger = LoggerFactory.getLogger(PaymentSteps.class);

    Customer customer;
    VendingMachine vendingMachine = new VendingMachine();
    boolean paymentStatus;
    long responseTime;
    String message;

    @Given("the customer is logged in with a cash account balance of {int}€")
    public void the_customer_is_logged_in_with_a_cash_account_balance_of_€(Integer int1) {
        // Write code here that turns the phrase above into concrete actions

    }
    @Given("the customer has selected items worth {int}€")
    public void the_customer_has_selected_items_worth_€(Integer int1) {
        // Write code here that turns the phrase above into concrete actions

    }
    @When("the customer views the payment options")
    public void the_customer_views_the_payment_options() {
        // Write code here that turns the phrase above into concrete actions

    }
    @Then("the available methods should be:")
    public void the_available_methods_should_be(io.cucumber.datatable.DataTable dataTable) {
        // Write code here that turns the phrase above into concrete actions
        // For automatic transformation, change DataTable to one of
        // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
        // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
        // Double, Byte, Short, Long, BigInteger or BigDecimal.
        //
        // For other transformations you can register a DataTableType.

    }
    @Then("cash account should not be visible")
    public void cash_account_should_not_be_visible() {
        // Write code here that turns the phrase above into concrete actions

    }


    @Given("a customer has a cash account balance of {int}€")
    public void customer_has_cash_balance(int balance) {
        logger.info("Initializing customer with balance: {}€", balance);
        customer = new Customer(balance);
        assertTrue("Customer login failed", customer.isLoggedIn());
    }

    @And("the customer selects items worth {int}€")
    public void customer_selects_items(int totalAmount) {
        logger.info("Customer selects items worth: {}€", totalAmount);
        vendingMachine.selectItems(totalAmount);
    }

    @When("they choose to pay using the cash account")
    public void user_pays_using_cash_account() {
        logger.info("Customer attempts to pay using Cash Account...");
        long startTime = System.currentTimeMillis();
        paymentStatus = vendingMachine.processPayment(customer);
        message = vendingMachine.getMessage();
        responseTime = System.currentTimeMillis() - startTime;

        if (paymentStatus) {
            logger.info("Payment processed successfully. Message: {}", message);
        } else {
            logger.warn("Payment failed. Message: {}", message);
        }
        logger.debug("Payment response time: {} ms", responseTime);
    }

    @Then("the payment should be successful")
    public void assert_payment_successful() {
        logger.info("Verifying successful payment...");
        assertTrue("Expected payment to be successful", paymentStatus);
        assertEquals("Success", message);
    }

    @Then("the payment should fail")
    public void assert_payment_failed() {
        logger.info("Verifying failed payment...");
        assertFalse("Expected payment to fail", paymentStatus);
        assertEquals("Insufficient funds", message);
    }

    @And("the remaining balance should be {int}€")
    public void check_remaining_balance(int expectedBalance) {
        logger.info("Verifying remaining balance is {}€", expectedBalance);
        assertEquals("Remaining balance mismatch", expectedBalance, customer.getCashAccount());
    }

    @And("a success message is shown within {int} seconds")
    public void success_message_shown_within(int seconds) {
        logger.info("Checking if success message displayed within {} seconds", seconds);
        assertTrue("Success message took too long", responseTime <= seconds * 1000);
    }

    @And("an error message is shown within {int} seconds")
    public void error_message_shown_within(int seconds) {
        logger.info("Checking if error message displayed within {} seconds", seconds);
        assertTrue("Error message took too long", responseTime <= seconds * 1000);
    }

    @And("an error message {string} should be shown")
    public void specific_error_message_shown(String expectedMessage) {
        logger.info("Verifying error message content: expected '{}'", expectedMessage);
        assertEquals("Error message mismatch", expectedMessage, message);
    }

    @When("the system displays payment options")
    public void system_displays_payment_options() {
        logger.info("System displaying available payment options...");
        // no-op, validation happens next
    }

    @Then("the options should include:")
    public void options_should_include(List<String> expectedOptions) {
        List<String> actual = vendingMachine.getAvailablePaymentMethods(customer);
        logger.info("Expected options: {}", expectedOptions);
        logger.info("Actual options:   {}", actual);
        assertTrue("Missing expected payment methods", actual.containsAll(expectedOptions));
    }

    @And("should not include:")
    public void options_should_not_include(List<String> notExpectedOptions) {
        List<String> actual = vendingMachine.getAvailablePaymentMethods(customer);
        logger.info("Ensuring these options are not shown: {}", notExpectedOptions);
        for (String method : notExpectedOptions) {
            assertFalse("Unexpected method found: " + method, actual.contains(method));
        }
    }
}
