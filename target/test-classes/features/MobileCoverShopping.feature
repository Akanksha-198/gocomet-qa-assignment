# Feature: Mobile Cover Shopping on CaseKaro
# Author: GoComet QA Intern Assignment
# Website: https://casekaro.com/

Feature: Mobile Cover Shopping on CaseKaro

  Background:
    Given the user is on the CaseKaro homepage

  Scenario: Search for Apple brand and add iPhone 16 Pro case in all 3 materials to cart
    When the user clicks on "Mobile Covers" menu
    And the user clicks the search button and types "Apple"
    Then only Apple brand results should be visible
    And no other brand results should be visible
    When the user clicks on "Apple" brand
    And the user searches for model "Iphone 16 pro"
    And the user clicks "Choose options" for the first item
    And the user adds the "Hard" material case to cart
    And the user goes back to product options
    And the user adds the "Soft" material case to cart
    And the user goes back to product options
    And the user adds the "Glass" material case to cart
    And the user opens the cart
    Then the cart should contain 3 items with different materials
    And the cart should have items with materials "Hard", "Soft", and "Glass"
    And the price details for all items should be printed in console
