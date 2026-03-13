package pages;

import com.microsoft.playwright.*;

/**
 * Manages Playwright browser lifecycle.
 * Single instance shared across step definitions via PicoContainer.
 */
public class PlaywrightManager {

    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;

    public PlaywrightManager() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setSlowMo(300)
        );
        context = browser.newContext(
                new Browser.NewContextOptions()
                        .setViewportSize(1440, 900)
        );
        page = context.newPage();
    }

    public Page getPage() {
        return page;
    }

    public void close() {
        if (page != null) page.close();
        if (context != null) context.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}
