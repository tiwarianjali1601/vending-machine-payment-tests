package com.gym.vending.stepdefs;

import com.gym.vending.app.*;
import com.gym.vending.pages.*;
import com.gym.vending.simulator.RandomScenarioGenerator;
import com.gym.vending.simulator.TestScenario;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class PaymentSteps {
    private static final Logger logger = LoggerFactory.getLogger(PaymentSteps.class);
    private CustomerSession customerSession;
    private PaymentProcessor paymentProcessor;
    private PaymentResult paymentResult;
    private List<String> availablePaymentMethods;
    private Map<String, String> testValues = new HashMap<>();

    @Given("the system is ready with a new customer")
    public void the_system_is_ready_with_a_new_customer() {
        logger.info("🔧 System initialization step for reusable Background");
        // Optional setup logic
    }

    @Given("a customer has a cash account balance of {int}€")
    public void customer_has_cash_balance(int balance) {
        logger.info("Initializing customer with balance: {}€", balance);
        CashAccount cashAccount = new CashAccount((double) balance);
        customerSession = new CustomerSession(cashAccount);
        paymentProcessor = new PaymentProcessor();
        testValues.put("Initial Balance", balance + "€");
    }

    @Given("the customer is logged in with a cash account balance of {int}€")
    public void the_customer_is_logged_in_with_a_cash_account_balance_of_€(Integer balance) {
        customer_has_cash_balance(balance);
    }

    @Given("the customer selects a {string} with quantity {int}")
    public void customer_selects_item_with_quantity(String itemName, int quantity) {
        Item item = createItem(itemName, quantity);
        customerSession.addToCart(item);
        logger.info("🛒 Added item to cart: {} x{}", item.getName(), item.getQty());
        testValues.put("Selected Item", item.getName());
        testValues.put("Selected Quantity", String.valueOf(quantity));
    }

    private Item createItem(String name, int qty) {
        switch (name.toLowerCase()) {
            case "protein bar": return new ProteinBar(qty);
            case "energy drink": return new EnergyDrink(qty);
            case "towel": return new Towel(qty);
            default: throw new IllegalArgumentException("Unsupported item type: " + name);
        }
    }

    @When("the system displays payment options")
    @When("the customer views the payment options")
    public void system_displays_payment_options() {
        availablePaymentMethods = paymentProcessor.getAvailablePaymentMethods(customerSession);
        logger.info("💳 Available payment methods: {}", availablePaymentMethods);
        testValues.put("Available Payment Methods", String.join(", ", availablePaymentMethods));
    }

    @Then("the options should include:")
    public void the_options_should_include(List<String> expectedOptions) {
        assertAllPresent(normalize(expectedOptions), normalize(availablePaymentMethods));
        testValues.put("Expected Payment Methods", String.join(", ", expectedOptions));
    }

    @And("should not include:")
    public void should_not_include(List<String> notExpectedOptions) {
        List<String> normalizedNotExpected = normalize(notExpectedOptions);
        List<String> normalizedActual = normalize(availablePaymentMethods);
        for (String option : normalizedNotExpected) {
            if (normalizedActual.contains(option)) {
                throw new AssertionError("❌ Unexpected payment method found: " + option);
            }
        }
        testValues.put("Excluded Payment Methods", String.join(", ", notExpectedOptions));
    }

    @Then("the available methods should be:")
    public void the_available_methods_should_be(DataTable dataTable) {
        List<String> expected = normalize(dataTable.asList());
        List<String> actual = normalize(availablePaymentMethods);
        assertAllPresent(expected, actual);
        assertAllPresent(actual, expected); // exact match only
        testValues.put("Expected Methods", String.join(", ", expected));
    }

    @And("cash account should not be visible")
    public void cash_account_should_not_be_visible() {
        for (String method : availablePaymentMethods) {
            if ("cash account".equalsIgnoreCase(method)) {
                throw new AssertionError("❌ Cash account should not be visible.");
            }
        }
        testValues.put("Cash Account Visible", "false");
    }

    @Then("cash account should be visible only when sufficient funds are available")
    public void cash_account_should_be_visible_only_when_sufficient_funds() {
        boolean isPresent = availablePaymentMethods.stream().anyMatch(m -> m.equalsIgnoreCase("Cash Account"));
        double balance = customerSession.getCashAccount().getBalance();
        double total = customerSession.getCartTotal();

        if (balance >= total && !isPresent)
            throw new AssertionError("💸 Cash Account should be visible but it's not.");
        if (balance < total && isPresent)
            throw new AssertionError("💸 Cash Account is visible despite insufficient balance.");
    }

    @When("they choose to pay using the cash account")
    public void user_pays_using_cash_account() {
        logger.info("💳 Attempting payment with Cash Account");
        paymentResult = paymentProcessor.processPayment(customerSession, "Cash Account");
        logger.info("🧾 Payment result: {}", paymentResult.getMessage());
        testValues.put("Payment Result", paymentResult.getMessage());
    }

    @When("the customer attempts to make the payment")
    public void the_customer_attempts_to_make_the_payment() {
        availablePaymentMethods = paymentProcessor.getAvailablePaymentMethods(customerSession);
        if (availablePaymentMethods.contains("Cash Account")) {
            paymentResult = paymentProcessor.processPayment(customerSession, "Cash Account");
        } else {
            paymentResult = new PaymentResult(false, "Cash Account not available", 0);
        }
        testValues.put("Payment Message", paymentResult.getMessage());
    }

    @Then("the payment result should match the expected outcome")
    public void the_payment_result_should_match_expected_outcome() {
        double total = customerSession.getCartTotal();
        double balance = customerSession.getCashAccount().getBalance();
        boolean shouldSucceed = balance >= total;

        if (shouldSucceed && !paymentResult.isSuccess()) {
            throw new AssertionError("Expected payment to succeed, but it failed.");
        }
        if (!shouldSucceed && paymentResult.isSuccess()) {
            throw new AssertionError("Expected payment to fail, but it succeeded.");
        }
    }

    @Then("the remaining balance should be correct")
    public void the_remaining_balance_should_be_correct() {
        double cartTotal = customerSession.getCartTotal();
        double initialBalance = customerSession.getCashAccount().getInitialBalance(); // you'll need to track this
        double expected = paymentResult.isSuccess() ? initialBalance - cartTotal : initialBalance;
        double actual = customerSession.getCashAccount().getBalance();

        if (Math.abs(expected - actual) > 0.01) {
            throw new AssertionError(
                    String.format("Expected remaining balance: %.2f€, but got %.2f€", expected, actual)
            );
        }
    }


    @Then("the payment should be successful")
    public void assert_payment_successful() {
        if (!paymentResult.isSuccess()) {
            throw new AssertionError("Payment failed: " + paymentResult.getMessage());
        }
    }

    @Then("the remaining balance should be {double}€")
    public void the_remaining_balance_should_be_double(Double expected) {
        double actual = customerSession.getCashAccount().getBalance();
        if (Math.abs(expected - actual) > 0.01) {
            throw new AssertionError(String.format("Expected %.2f€, got %.2f€", expected, actual));
        }
    }

    @And("a success message is shown within {int} seconds")
    public void success_message_shown_within(int seconds) {
        if (paymentResult.getResponseTime() > seconds * 1000) {
            throw new AssertionError("⚠️ Success message delayed: took more than " + seconds + " seconds.");
        }
    }

    @Then("the available payment methods should be correct")
    public void the_available_payment_methods_should_be_correct() {
        double cartTotal = customerSession.getCartTotal();
        double balance = customerSession.getCashAccount().getBalance();
        boolean hasCash = paymentProcessor.getAvailablePaymentMethods(customerSession)
                .stream().anyMatch(m -> m.equalsIgnoreCase("Cash Account"));

        if (balance >= cartTotal && !hasCash) {
            throw new AssertionError("Expected Cash Account to be shown, but it isn't.");
        }
        if (balance < cartTotal && hasCash) {
            throw new AssertionError("Cash Account should not be available with insufficient balance.");
        }
    }

    @Given("a random customer scenario is generated")
    public void a_random_customer_scenario_is_generated() {
        TestScenario scenario = RandomScenarioGenerator.generateRandomScenario();
        customerSession = scenario.getSession();
        paymentProcessor = new PaymentProcessor();
        logger.info("🧪 Random scenario: Balance = {}€, Cart = {}€",
                customerSession.getCashAccount().getBalance(),
                customerSession.getCartTotal());
    }

    @Then("the response time should be within {int} seconds")
    public void the_response_time_should_be_within_seconds(Integer seconds) {
        if (paymentResult.getResponseTime() > seconds * 1000) {
            throw new AssertionError("⏱️ Response took too long: " + paymentResult.getResponseTime() + "ms");
        }
    }

    private List<String> normalize(List<String> list) {
        List<String> result = new ArrayList<>();
        for (String s : list) {
            result.add(s.toLowerCase().trim());
        }
        return result;
    }

    private void assertAllPresent(List<String> expected, List<String> actual) {
        for (String e : expected) {
            if (!actual.contains(e)) {
                throw new AssertionError("Missing expected option: " + e);
            }
        }
    }

    public Map<String, String> getTestValues() {
        return testValues;
    }
}
