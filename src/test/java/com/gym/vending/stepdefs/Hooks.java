package com.gym.vending.stepdefs;

import com.gym.vending.utils.TestReportGenerator;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class Hooks {

    private static final Logger logger = LoggerFactory.getLogger(Hooks.class);
    private static int totalScenarios = 0;
    private static int completedScenarios = 0;

    // ✅ Let Cucumber inject PaymentSteps instead of creating it manually
    private final PaymentSteps paymentSteps;

    // ✅ Constructor-based dependency injection (required by Cucumber)
    public Hooks(PaymentSteps paymentSteps) {
        this.paymentSteps = paymentSteps;
    }

    @Before
    public void beforeScenario(Scenario scenario) {
        totalScenarios++;
        logger.info("🔄 Starting Scenario: {}", scenario.getName());
    }

    @After
    public void afterScenario(Scenario scenario) {
        completedScenarios++;
        boolean passed = !scenario.isFailed();
        if (passed) {
            logger.info("✅ Scenario PASSED: {}", scenario.getName());
        } else {
            logger.error("❌ Scenario FAILED: {}", scenario.getName());
        }

        Map<String, String> testValues = paymentSteps.getTestValues();
        testValues.put("Scenario Name", scenario.getName());
        testValues.put("Status", passed ? "PASSED" : "FAILED");

        scenario.getSourceTagNames().forEach(tag -> testValues.put("Tag", tag));
        TestReportGenerator.addScenarioResult(scenario.getName(), passed, testValues);

        if (completedScenarios == totalScenarios) {
            logger.info("All scenarios completed. Generating final test report...");
            TestReportGenerator.generateReport();
        }
    }
}
