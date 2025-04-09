@Regression
Feature: Validate Available Payment Methods

  Scenario: Show only valid payment options based on cash account balance
    Given a customer has a cash account balance of 5€
    And the customer selects items worth 15€
    When the system displays payment options
    Then the options should include:
      | Debit/Credit Card |
      | Apple Pay         |
      | Google Pay        |
    And should not include:
      | Cash Account      |
