package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.junit.jupiter.api.Assertions;

public class HomePage {

    private final Page page;
    private static final String BASE_URL = "https://casekaro.com/";
    private static final String MODEL_SEARCH_URL = "https://casekaro.com/pages/phone-cases-by-model";

    public HomePage(Page page) {
        this.page = page;
    }

    public void navigateToHomePage() {
        page.navigate(BASE_URL);
        page.waitForLoadState();
        Assertions.assertTrue(page.url().contains("casekaro.com"), "Should be on CaseKaro website");
        System.out.println("✅ Navigated to: " + page.url());
    }

    public void clickMobileCovers() {
        // Click "Mobile Covers" in the nav menu
        Locator mobileCoversLink = page.locator("a:has-text('Mobile Covers'), a[href*='phone-cases-by-model']").first();
        if (mobileCoversLink.count() > 0 && mobileCoversLink.isVisible()) {
            mobileCoversLink.click();
            page.waitForLoadState();
        } else {
            // Fallback: direct navigation
            page.navigate(MODEL_SEARCH_URL);
            page.waitForLoadState();
        }
        page.waitForTimeout(1500);
        System.out.println("✅ On Mobile Covers page: " + page.url());
    }

    public void searchBrandInModelSearch(String brand) {
        // Use the search box on phone-cases-by-model page
        Locator searchBox = page.locator("input[placeholder*='Search'], input[type='search'], input[type='text']").first();
        searchBox.waitFor();
        searchBox.fill(brand);
        page.waitForTimeout(2000);
        System.out.println("✅ Searched for brand: " + brand);
    }
}
