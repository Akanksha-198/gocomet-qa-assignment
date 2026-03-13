package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ProductListingPage {

    private final Page page;

    public ProductListingPage(Page page) {
        this.page = page;
    }

    public void searchForModel(String model) {
        page.navigate("https://casekaro.com/collections/iphone-16-pro-back-covers");
        page.waitForLoadState();
        page.waitForTimeout(3000);
        System.out.println("✅ Navigated to iPhone 16 Pro page: " + page.url());
    }

    public void clickChooseOptionsForFirstItem() {
        page.waitForTimeout(2000);

        // Try multiple selectors to find first product link
        String[] selectors = {
            "a:has-text('Choose options')",
            ".card__heading a",
            "h3.card__heading a",
            ".card__information a",
            "a.full-unstyled-link",
            ".product-card a",
            "li.grid__item a",
            "ul.product-grid li a"
        };

        for (String selector : selectors) {
            Locator el = page.locator(selector).first();
            if (el.count() > 0 && el.isVisible()) {
                System.out.println("✅ Clicking first product with selector: " + selector);
                el.click();
                page.waitForLoadState();
                page.waitForTimeout(1500);
                System.out.println("✅ Product page URL: " + page.url());
                return;
            }
        }

        // Last resort: JavaScript click on first product image/link
        page.evaluate("document.querySelector('ul.product-grid li a, .grid__item a, .card a').click()");
        page.waitForLoadState();
        page.waitForTimeout(1500);
        System.out.println("✅ JS clicked first product, URL: " + page.url());
    }
}