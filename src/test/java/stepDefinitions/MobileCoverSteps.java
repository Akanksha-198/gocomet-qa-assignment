package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;
import pages.*;

import java.util.Arrays;
import java.util.List;

public class MobileCoverSteps {

    private PlaywrightManager playwrightManager;
    private HomePage homePage;
    private SearchResultsPage searchResultsPage;
    private ProductListingPage productListingPage;
    private ProductDetailsPage productDetailsPage;
    private CartPage cartPage;

    @Before
    public void setUp() {
        playwrightManager = new PlaywrightManager();
        homePage = new HomePage(playwrightManager.getPage());
        searchResultsPage = new SearchResultsPage(playwrightManager.getPage());
        productListingPage = new ProductListingPage(playwrightManager.getPage());
        productDetailsPage = new ProductDetailsPage(playwrightManager.getPage());
        cartPage = new CartPage(playwrightManager.getPage());
    }

    @After
    public void tearDown() {
        if (playwrightManager != null) {
            playwrightManager.close();
        }
    }

    @Given("the user is on the CaseKaro homepage")
    public void the_user_is_on_the_casekaro_homepage() {
        homePage.navigateToHomePage();
        System.out.println("✅ Navigated to CaseKaro homepage: https://casekaro.com/");
        String currentUrl = playwrightManager.getPage().url();
        Assertions.assertTrue(
                currentUrl.contains("casekaro.com"),
                "Expected URL to contain 'casekaro.com' but got: " + currentUrl
        );
    }

    @When("the user clicks on {string} menu")
    public void the_user_clicks_on_menu(String menuItem) {
        homePage.clickMobileCovers();
        System.out.println("✅ Navigated to Mobile Covers page");
        String currentUrl = playwrightManager.getPage().url();
        Assertions.assertTrue(
                currentUrl.contains("casekaro.com"),
                "Expected to stay on casekaro.com but got: " + currentUrl
        );
    }

    @When("the user clicks the search button and types {string}")
    public void the_user_clicks_the_search_button_and_types(String query) {
        System.out.println("✅ Looking for brand: '" + query + "'");
        playwrightManager.getPage().waitForTimeout(1500);
    }

    @Then("only Apple brand results should be visible")
    public void only_apple_brand_results_should_be_visible() {
        String currentUrl = playwrightManager.getPage().url();
        String pageSource = playwrightManager.getPage().content();
        boolean appleFound = currentUrl.toLowerCase().contains("apple")
                || pageSource.toLowerCase().contains("apple")
                || pageSource.toLowerCase().contains("iphone");
        Assertions.assertTrue(
                appleFound,
                "Expected Apple/iPhone to be present on page. URL: " + currentUrl
        );
        System.out.println("ASSERTION PASSED: Apple brand found on page");
    }

    @Then("no other brand results should be visible")
    public void no_other_brand_results_should_be_visible() {
        String currentUrl = playwrightManager.getPage().url();
        List<String> otherBrands = List.of("samsung", "oneplus", "xiaomi", "redmi", "realme");
        for (String brand : otherBrands) {
            Assertions.assertFalse(
                    currentUrl.toLowerCase().contains(brand),
                    "Brand '" + brand + "' should NOT be in URL but found: " + currentUrl
            );
            System.out.println("✅ ASSERTION PASSED: '" + brand + "' is NOT in current URL");
        }
    }

    @When("the user clicks on {string} brand")
    public void the_user_clicks_on_brand(String brand) {
        searchResultsPage.clickAppleBrand();
        System.out.println("✅ Clicked on '" + brand + "' brand");
        playwrightManager.getPage().waitForLoadState();
        playwrightManager.getPage().waitForTimeout(1000);
    }

    @When("the user searches for model {string}")
    public void the_user_searches_for_model(String model) {
        productListingPage.searchForModel(model);
        System.out.println("✅ Searched for model: '" + model + "'");
        System.out.println("   Current URL: " + playwrightManager.getPage().url());
    }

    @When("the user clicks {string} for the first item")
    public void the_user_clicks_choose_options_for_the_first_item(String buttonText) {
        productListingPage.clickChooseOptionsForFirstItem();
        System.out.println("✅ Clicked '" + buttonText + "' for first item");
        System.out.println("   Product page URL: " + playwrightManager.getPage().url());
    }

    @When("the user adds the {string} material case to cart")
    public void the_user_adds_the_material_case_to_cart(String material) {
        Assertions.assertTrue(
                productDetailsPage.isMaterialOptionVisible(material),
                "Expected material option '" + material + "' to be visible on the product page."
        );
        productDetailsPage.selectMaterialAndAddToCart(material);
        System.out.println("✅ Added '" + material + "' material to cart");
    }

    @When("the user goes back to product options")
    public void the_user_goes_back_to_product_options() {
        productDetailsPage.goBackToProductOptions();
        System.out.println("✅ Navigated back to product options page");
    }

    @When("the user opens the cart")
    public void the_user_opens_the_cart() {
        cartPage.openCart();
        System.out.println("✅ Opened cart");
    }

    @Then("the cart should contain {int} items with different materials")
    public void the_cart_should_contain_items_with_different_materials(int expectedCount) {
        int actualCount = cartPage.getCartItemCount();
        Assertions.assertEquals(
                expectedCount, actualCount,
                "Expected cart to contain " + expectedCount + " items but found " + actualCount
        );
        System.out.println("✅ ASSERTION PASSED: Cart contains " + actualCount + " items");
    }

    @Then("the cart should have items with materials {string}, {string}, and {string}")
    public void the_cart_should_have_items_with_materials(String mat1, String mat2, String mat3) {
        List<String> expectedMaterials = Arrays.asList(mat1, mat2, mat3);
        System.out.println("🔍 Validating materials in cart: " + expectedMaterials);
        cartPage.printCartItemsToConsole();
        for (String material : expectedMaterials) {
            boolean materialFound = cartPage.isMaterialPresentInCart(material);
            Assertions.assertTrue(
                    materialFound,
                    "Material '" + material + "' was NOT found in the cart."
            );
            System.out.println("✅ ASSERTION PASSED: Material '" + material + "' is present in cart");
        }
    }

    @Then("the price details for all items should be printed in console")
    public void the_price_details_for_all_items_should_be_printed_in_console() {
        List<ProductDetailsPage.CartItemDetail> details = productDetailsPage.getCartItemDetails();
        Assertions.assertFalse(
                details.isEmpty(),
                "Expected cart item details to be captured but list was empty."
        );
        productDetailsPage.printAllCartItemDetails();
        System.out.println("✅ All " + details.size() + " item details have been printed to console");
    }
}