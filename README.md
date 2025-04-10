# Gym Vending Machine Payment System Test Framework

## Overview
This test automation framework validates the payment system for a gym's vending machine, focusing on cash account payments and payment method validations. The framework uses Cucumber BDD for test automation with Allure reporting for comprehensive test result visualization.

## Features
- Cash account payment validation
- Payment method availability checks
- Balance verification
- Transaction processing
- Response time validation
- Automated test reporting with Allure
- Random scenario generation

## Prerequisites
- Java JDK 11 or higher
- Maven 3.6.3 or higher
- IDE (IntelliJ IDEA recommended)
- Git
- Allure Command Line Tool

### Installing Allure Command Line Tool
```bash
# For MacOS:
brew install allure

# For Windows (using Scoop):
scoop install allure

# For Linux:
sudo apt-add-repository ppa:qameta/allure
sudo apt-get update
sudo apt-get install allure
```

## Project Structure
```
src/
├── main/java/com/gym/vending/
│   ├── app/
│   │   ├── CashAccount.java
│   │   ├── CustomerSession.java
│   │   ├── PaymentProcessor.java
│   │   └── PaymentResult.java
│   ├── simulator/
│   │   ├── RandomScenarioGenerator.java
│   │   └── TestScenario.java
│   └── utils/
│       └── TestReportGenerator.java
└── test/
    ├── java/com/gym/vending/
    │   ├── runners/
    │   │   └── TestRunner.java
    │   └── stepdefs/
    │       └── PaymentSteps.java
    └── resources/
        ├── features/
        │   ├── successful_cash_account_payment.feature
        │   ├── insufficient_funds_payment.feature
        │   └── payment_method_validation.feature
        └── allure.properties
```

## Installation
1. Clone the repository:
```bash
git clone <repository-url>
cd gym-vending-payment-tests
```

2. Install dependencies:
```bash
mvn clean install
```

## Running Tests and Generating Reports

### Run Specific Test Categories
```bash
# Run smoke tests
mvn clean test -Dcucumber.filter.tags="@Smoke" ; allure serve target/allure-results

# Run regression tests
mvn clean test -Dcucumber.filter.tags="@Regression" ; allure serve target/allure-results
```

### Run Specific Feature File
```bash
mvn clean test -Dcucumber.features="src/test/resources/features/successful_cash_account_payment.feature" -Dcucumber.filter.tags="@Smoke or @Regression" allure:serve
```

## Test Reports
Allure provides comprehensive test reports including:
- Test execution summary
- Feature-wise breakdown
- Step-by-step execution details
- Screenshots for failed tests
- Test execution timeline
- Environment details
- Failure analysis and trends

The Allure report will automatically open in your default browser after test execution. The report includes:
- Detailed test execution results
- Test step details
- Execution time
- Failure screenshots and logs
- Test execution trends

## Logging
The framework uses SLF4J with Logback for logging:
- Runtime logs: `target/test.log`
- Test execution logs: Available in Allure reports
- Debug logs: Enable in `src/test/resources/logback.xml`
