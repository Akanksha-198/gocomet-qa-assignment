package pages;

import com.microsoft.playwright.Page;

import java.util.List;

public class SearchResultsPage {

    private final Page page;

    public SearchResultsPage(Page page) {
        this.page = page;
    }

    public List<String> getVisibleSearchResultTexts() {
        page.waitForTimeout(1500);
        return page.locator("a").allInnerTexts();
    }

    public boolean isAppleVisible() {
        String content = page.content().toLowerCase();
        return content.contains("apple") || content.contains("iphone");
    }

    public boolean isOtherBrandVisible(String brandName) {
        return page.locator(
                "a:visible:has-text('" + brandName + "')"
        ).count() > 0;
    }

    public List<String> getOtherBrandsToValidate() {
        return List.of("Samsung", "OnePlus", "Xiaomi", "Redmi", "Realme",
                "OPPO", "Vivo", "Motorola", "Nokia");
    }

public void clickAppleBrand() {
    // Navigate directly to iPhone 16 Pro back covers
    page.navigate("https://casekaro.com/collections/iphone-16-pro-back-covers");
    page.waitForLoadState();
    page.waitForTimeout(1000);
    System.out.println("✅ Navigated to iPhone 16 Pro covers: " + page.url());
}
}