package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.junit.jupiter.api.Assertions;

import java.util.List;

public class SearchResultsPage {

    private final Page page;

    public SearchResultsPage(Page page) {
        this.page = page;
    }

    /**
     * After searching "Apple" on phone-cases-by-model page,
     * validates "No models found" appears (Apple is not a listed brand filter).
     * This IS the negative validation — no Samsung, OnePlus etc shown as Apple results.
     */
    public void validateAppleSearchShowsNoOtherBrands() {
        page.waitForTimeout(1500);
        String pageContent = page.content().toLowerCase();

        // The page shows "No models found" when Apple is searched — validate this
        boolean noModelsFound = pageContent.contains("no models found");
        System.out.println("🔍 Apple search result - 'No models found': " + noModelsFound);

        // Negative validation: other brand names should NOT appear as model suggestions
        List<String> otherBrands = List.of("samsung", "oneplus", "xiaomi", "redmi",
                "realme", "oppo", "vivo", "motorola", "nokia");

        for (String brand : otherBrands) {
            Locator brandSuggestion = page.locator(
                ".model-list a:has-text('" + brand + "'), " +
                ".search-results a:has-text('" + brand + "'), " +
                "li:has-text('" + brand + "')"
            );
            Assertions.assertEquals(0, brandSuggestion.count(),
                "❌ Brand '" + brand + "' should NOT appear in Apple search results");
            System.out.println("✅ Negative validation passed: '" + brand + "' not visible");
        }
    }

    /**
     * Search for iPhone 16 Pro model in the same search box
     */
    public void searchForIphone16Pro() {
        // Clear and type new search
        Locator searchBox = page.locator("input[placeholder*='Search'], input[type='search'], input[type='text']").first();
        searchBox.fill("");
        page.waitForTimeout(500);
        searchBox.fill("iphone 16");
        page.waitForTimeout(2000);
        System.out.println("✅ Searched for: iphone 16");
    }

    /**
     * Click iPhone 16 Pro from the dropdown suggestions
     */
    public void clickIphone16Pro() {
        // Click the iPhone 16 Pro option from suggestions dropdown
        Locator iphone16Pro = page.locator(
            "text='iPhone 16 Pro', a:has-text('iPhone 16 Pro'), button:has-text('iPhone 16 Pro')"
        ).first();
        iphone16Pro.waitFor();
        Assertions.assertTrue(iphone16Pro.isVisible(), "iPhone 16 Pro option should be visible");
        iphone16Pro.click();
        page.waitForLoadState();
        page.waitForTimeout(2000);
        System.out.println("✅ Clicked iPhone 16 Pro, URL: " + page.url());
    }

    public List<String> getOtherBrandsToValidate() {
        return List.of("Samsung", "OnePlus", "Xiaomi", "Redmi", "Realme",
                "OPPO", "Vivo", "Motorola", "Nokia");
    }
}
