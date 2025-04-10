package com.gym.vending.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class TestReportGenerator {
    private static final Logger logger = LoggerFactory.getLogger(TestReportGenerator.class);
    private static final String REPORTS_DIR = "target/test-reports";
    private static final List<ScenarioResult> scenarioResults = new ArrayList<>();

    private static class ScenarioResult {
        String name;
        boolean passed;
        Map<String, String> testValues;

        ScenarioResult(String name, boolean passed, Map<String, String> testValues) {
            this.name = name;
            this.passed = passed;
            this.testValues = testValues;
        }
    }

    public static void addScenarioResult(String scenarioName, boolean passed, Map<String, String> testValues) {
        logger.info("Adding scenario result: {} - {} with values: {}", scenarioName, passed ? "PASSED" : "FAILED", testValues);
        scenarioResults.add(new ScenarioResult(scenarioName, passed, testValues));
    }

    public static void generateReport() {
        try {
            // Create reports directory if it doesn't exist
            File reportsDir = new File(REPORTS_DIR);
            if (!reportsDir.exists()) {
                if (!reportsDir.mkdirs()) {
                    logger.error("Failed to create reports directory: {}", REPORTS_DIR);
                    return;
                }
            }

            // Generate report filename with timestamp
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            String reportFileName = REPORTS_DIR + "/test_summary_" + timestamp + ".txt";
            File reportFile = new File(reportFileName);

            try (FileWriter writer = new FileWriter(reportFile)) {
                // Calculate statistics
                int totalScenarios = scenarioResults.size();
                int passedScenarios = 0;
                for (ScenarioResult result : scenarioResults) {
                    if (result.passed) {
                        passedScenarios++;
                    }
                }

                int failedScenarios = totalScenarios - passedScenarios;
                double passRate = totalScenarios > 0 ? (double) passedScenarios / totalScenarios * 100 : 0;

                logger.info("Generating report for {} scenarios", totalScenarios);

                // Write report header
                writer.write("==========================================\n");
                writer.write("         VENDING MACHINE TEST REPORT       \n");
                writer.write("==========================================\n\n");

                // Write execution summary
                writer.write("EXECUTION SUMMARY:\n");
                writer.write("------------------\n");
                writer.write(String.format("Total Scenarios: %d\n", totalScenarios));
                writer.write(String.format("Passed: %d\n", passedScenarios));
                writer.write(String.format("Failed: %d\n", failedScenarios));
                writer.write(String.format("Pass Rate: %.0f%%\n\n", passRate));

                // Write detailed results
                writer.write("DETAILED RESULTS:\n");
                writer.write("-----------------\n");
                for (ScenarioResult result : scenarioResults) {
                    writer.write(String.format("%s %s - %s\n",
                            result.passed ? "✅" : "❌",
                            result.name,
                            result.passed ? "PASSED" : "FAILED"));
                    
                    // Write test values if they exist
                    if (result.testValues != null && !result.testValues.isEmpty()) {
                        writer.write("    Test Values:\n");
                        for (Map.Entry<String, String> entry : result.testValues.entrySet()) {
                            writer.write(String.format("        %s: %s\n", entry.getKey(), entry.getValue()));
                        }
                    }
                    writer.write("\n");
                }

                writer.write("==========================================\n");
                writer.write("Report generated at: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n");
                writer.write("==========================================\n");

                logger.info("Test report generated at: {}", reportFile.getAbsolutePath());
            }
        } catch (IOException e) {
            logger.error("Error generating test report: {}", e.getMessage());
        }
    }

    public static void reset() {
        scenarioResults.clear();
    }
} 