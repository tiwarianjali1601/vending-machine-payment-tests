@Regression
Feature: Random Payment Scenarios
  As a vending machine system
  I want to test random payment scenarios
  So that I can verify the system handles various combinations of balances and purchases correctly

  @Smoke @Regression
  Scenario: Random Payment Test with Payment Method Validation
    Given a random customer scenario is generated
    When the system displays payment options
    Then the available payment methods should be correct
    And cash account should be visible only when sufficient funds are available
    When the customer attempts to make the payment
    Then the payment result should match the expected outcome
    And the remaining balance should be correct
    And the response time should be within 2 seconds
