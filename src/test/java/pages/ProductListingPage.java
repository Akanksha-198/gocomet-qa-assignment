package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.junit.jupiter.api.Assertions;

public class ProductListingPage {

    private final Page page;

    public ProductListingPage(Page page) {
        this.page = page;
    }

    public void verifyOnIphone16ProListingPage() {
        Assertions.assertTrue(page.url().contains("iphone-16-pro"),
            "Should be on iPhone 16 Pro listing page");
        System.out.println("✅ Confirmed on iPhone 16 Pro listing page: " + page.url());
    }

    public void clickChooseOptionsForFirstItem() {
        page.waitForTimeout(2000);

        // Try "Choose options" button first (explicit CTA)
        Locator chooseOptions = page.locator("a:has-text('Choose options')").first();
        if (chooseOptions.count() > 0 && chooseOptions.isVisible()) {
            System.out.println("✅ Clicking 'Choose options' button");
            chooseOptions.click();
            page.waitForLoadState();
            page.waitForTimeout(1500);
            System.out.println("✅ Product page URL: " + page.url());
            return;
        }

        // Fallback: click first product card heading/link
        String[] selectors = {
            ".card__heading a",
            "h3.card__heading a",
            ".card__information a",
            "a.full-unstyled-link",
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

        // Last resort: JavaScript
        page.evaluate("document.querySelector('ul.product-grid li a, .grid__item a, .card a').click()");
        page.waitForLoadState();
        page.waitForTimeout(1500);
        System.out.println("✅ JS clicked first product, URL: " + page.url());
    }
}
