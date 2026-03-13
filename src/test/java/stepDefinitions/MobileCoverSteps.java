package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;
import pages.*;

import java.util.List;

public class MobileCoverSteps {

    private final PlaywrightManager playwrightManager;
    private final HomePage homePage;
    private final SearchResultsPage searchResultsPage;
    private final ProductListingPage productListingPage;
    private final ProductDetailsPage productDetailsPage;
    private final CartPage cartPage;

    public MobileCoverSteps(PlaywrightManager playwrightManager) {
        this.playwrightManager = playwrightManager;
        var page = playwrightManager.getPage();
        this.homePage = new HomePage(page);
        this.searchResultsPage = new SearchResultsPage(page);
        this.productListingPage = new ProductListingPage(page);
        this.productDetailsPage = new ProductDetailsPage(page);
        this.cartPage = new CartPage(page);
    }

    @After
    public void tearDown() {
        playwrightManager.close();
    }

    // Step 1: Navigate to website
    @Given("I navigate to CaseKaro website")
    public void iNavigateToCaseKaroWebsite() {
        homePage.navigateToHomePage();
    }

    // Step 2: Click Mobile Covers
    @When("I click on Mobile Covers")
    public void iClickOnMobileCovers() {
        homePage.clickMobileCovers();
    }

    // Step 3: Search for Apple in the model search box
    @When("I search for {string} in the model search")
    public void iSearchForInTheModelSearch(String brand) {
        homePage.searchBrandInModelSearch(brand);
    }

    // Step 4: Negative validation - other brands not visible after Apple search
    @Then("other brands should not be visible in search results")
    public void otherBrandsShouldNotBeVisibleInSearchResults() {
        searchResultsPage.validateAppleSearchShowsNoOtherBrands();
    }

    // Step 5a: Search for iPhone 16 Pro model
    @When("I search for iPhone {string} model")
    public void iSearchForIphoneModel(String model) {
        searchResultsPage.searchForIphone16Pro();
    }

    // Step 5b: Click iPhone 16 Pro
    @When("I click on iPhone 16 Pro")
    public void iClickOnIPhone16Pro() {
        searchResultsPage.clickIphone16Pro();
        productListingPage.verifyOnIphone16ProListingPage();
    }

    // Step 6: Click Choose Options for first item
    @When("I click Choose Options for the first item")
    public void iClickChooseOptionsForTheFirstItem() {
        productListingPage.clickChooseOptionsForFirstItem();
    }

    // Step 7 & 8: Verify material exists and add to cart
    @Then("I should see {string} material option")
    public void iShouldSeeMaterialOption(String material) {
        boolean visible = productDetailsPage.isMaterialOptionVisible(material);
        Assertions.assertTrue(visible, "Material '" + material + "' should be visible on product page");
        System.out.println("✅ Material '" + material + "' is visible");
    }

    @When("I select {string} material and add to cart")
    public void iSelectMaterialAndAddToCart(String material) {
        productDetailsPage.selectMaterialAndAddToCart(material);
    }

    @When("I go back to product page to select another material")
    public void iGoBackToProductPage() {
        productDetailsPage.goBackToProductOptions();
    }

    // Step 9: Open cart
    @When("I open the cart")
    public void iOpenTheCart() {
        cartPage.openCart();
    }

    // Step 10: Validate all 3 items in cart
    @Then("the cart should contain {int} items")
    public void theCartShouldContainItems(int expectedCount) {
        int actualCount = cartPage.getCartItemCount();
        Assertions.assertEquals(expectedCount, actualCount,
            "Cart should have " + expectedCount + " items but found " + actualCount);
        System.out.println("✅ Cart contains " + actualCount + " items");
    }

    @Then("the cart should contain {string} material")
    public void theCartShouldContainMaterial(String material) {
        boolean present = cartPage.isMaterialPresentInCart(material);
        Assertions.assertTrue(present, "Cart should contain material: " + material);
        System.out.println("✅ Cart contains material: " + material);
    }

    // Step 11: Print all item details
    @Then("I print all cart item details to console")
    public void iPrintAllCartItemDetailsToConsole() {
        productDetailsPage.printAllCartItemDetails();
        cartPage.printCartItemsToConsole();
    }
}
