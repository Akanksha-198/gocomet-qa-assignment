Feature: Mobile Cover Shopping on CaseKaro

  Background:
    Given I navigate to CaseKaro website

  Scenario: Add all 3 materials of iPhone 16 Pro case to cart and validate
    # Step 2: Click Mobile Covers
    When I click on Mobile Covers

    # Step 3: Search Apple in model search box
    And I search for "Apple" in the model search

    # Step 4: Negative validation - other brands not visible
    Then other brands should not be visible in search results

    # Step 5: Search iPhone 16 Pro model and click it
    When I search for iPhone "16 Pro" model
    And I click on iPhone 16 Pro

    # Step 6: Click Choose Options for first item
    When I click Choose Options for the first item

    # Step 7 & 8: Add Hard material
    Then I should see "Hard" material option
    When I select "Hard" material and add to cart
    And I go back to product page to select another material

    # Step 7 & 8: Add Soft material
    Then I should see "Soft" material option
    When I select "Soft" material and add to cart
    And I go back to product page to select another material

    # Step 7 & 8: Add Glass material
    Then I should see "Glass" material option
    When I select "Glass" material and add to cart

    # Step 9: Open cart
    When I open the cart

    # Step 10: Validate all 3 items in cart
    Then the cart should contain 3 items
    And the cart should contain "Hard" material
    And the cart should contain "Soft" material
    And the cart should contain "Glass" material

    # Step 11: Print details to console
    Then I print all cart item details to console
