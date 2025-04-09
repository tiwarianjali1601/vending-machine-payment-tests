@Smoke @Regression
Feature: Successful Cash Payment

  Scenario: Payment is successful using cash account when sufficient funds are available
    Given a customer has a cash account balance of 20€
    And the customer selects items worth 15€
    When they choose to pay using the cash account
    Then the payment should be successful
    And the remaining balance should be 5€
    And a success message is shown within 3 seconds
