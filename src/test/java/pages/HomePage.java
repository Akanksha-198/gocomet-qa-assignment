package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class HomePage {

    private final Page page;
    private static final String BASE_URL = "https://casekaro.com/";

    public HomePage(Page page) {
        this.page = page;
    }

    public void navigateToHomePage() {
        page.navigate(BASE_URL);
        page.waitForLoadState();
    }

    public void clickMobileCovers() {
        // Direct navigation to mobile covers page
        page.navigate("https://casekaro.com/pages/phone-cases-by-model");
        page.waitForLoadState();
        page.waitForTimeout(1000);
    }

    public void clickSearchButton() {
        // Search button click - use JavaScript to open search modal
        page.evaluate("document.querySelector('.search-modal__open-button, button[aria-label=\"Search\"]').click()");
        page.waitForTimeout(1500);
    }

    public void typeInSearch(String query) {
        // Type directly into search input using JavaScript
        page.evaluate("var inputs = document.querySelectorAll('input[name=\"q\"]'); " +
                "for(var i=0; i<inputs.length; i++) { " +
                "  if(inputs[i].offsetParent !== null) { inputs[i].focus(); break; } " +
                "}");
        page.waitForTimeout(500);

        // Try all possible visible inputs
        Locator allInputs = page.locator("input[name='q']");
        int count = allInputs.count();
        for (int i = 0; i < count; i++) {
            Locator input = allInputs.nth(i);
            if (input.isVisible()) {
                input.fill(query);
                page.waitForTimeout(1000);
                return;
            }
        }
        // Last resort - type via keyboard after JS focus
        page.keyboard().type(query);
        page.waitForTimeout(1000);
    }
}