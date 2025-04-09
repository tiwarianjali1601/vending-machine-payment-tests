Feature: Validate available payment methods before checkout

  Scenario: Show available payment methods at checkout
    Given the customer is logged in with a cash account balance of 10€
    And the customer has selected items worth 15€
    When the customer views the payment options
    Then the available methods should be:
      | Debit/Credit card |
      | Apple Pay         |
      | Google Pay        |
    And cash account should not be visible
