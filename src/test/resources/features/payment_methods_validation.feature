@Regression
Feature: Validate Available Payment Methods

  Background:
    Given the system is ready with a new customer

  Scenario Outline: Show only valid options based on cash balance and item
    Given a customer has a cash account balance of <balance>€
    And the customer selects a "<item>" with quantity <qty>
    When the system displays payment options
    Then the options should include:
      | Debit/Credit Card |
      | Apple Pay         |
      | Google Pay        |
    And should not include:
      | Cash Account      |

    Examples:
      | balance | item         | qty |
      | 5       | Towel        | 2   |
      | 7       | Protein Bar  | 1   |
      | 6       | Energy Drink | 1   |
