

@Parallel
Feature: Product price analysis

  Scenario: Perform Various Actions on Basgar Website

    Given I navigate to the website
    When I verify website Url
    And I click on the login modüle
    And I enter my email and password
    When I click on the login button
    And I select a random category
    And I click on the fiyati düsenler
    And I click on the first product
    And I verify the highest price within the period
    And I verify the lowest price within the period
    And I verify the current lowest price
    And I export price to Excel file
    Then I export the product details to an Excel file
    And I go to the store with the cheapest price
    Then I verify the Url of the redirected store and go back to the previous window
    Then I log out


