package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.util.ArrayList;
import java.util.List;

/**
 * Page Object for the Cart on CaseKaro.
 * Handles cart opening and item validation.
 */
public class CartPage {

    private final Page page;

    // Cart locators
    private final String CART_ICON = "a[href='/cart'], .cart__icon-button, " +
            "button[aria-label*='cart'], button[aria-label*='Cart'], " +
            "a.header__icon--cart, .cart-count-bubble";

    private final String CART_DRAWER = ".cart-drawer, #cart-drawer, " +
            ".cart-notification, .cart__items, " +
            "[id*='CartDrawer'], .drawer[id*='cart']";

    private final String CART_ITEMS = ".cart-item, .cart__item, " +
            "[class*='cart-item'], .mini-cart__item";

    private final String CART_ITEM_MATERIAL = ".cart-item__details .product-option, " +
            ".cart-item__details dd, " +
            ".cart-item__details [class*='option'], " +
            ".cart-item__properties, " +
            "dl.product-option";

    public CartPage(Page page) {
        this.page = page;
    }

    public void openCart() {
        Locator cartIcon = page.locator(CART_ICON).first();
        cartIcon.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        cartIcon.click();
        page.waitForTimeout(1500);
    }

    public int getCartItemCount() {
        Locator items = page.locator(CART_ITEMS);
        items.first().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        return items.count();
    }

    public List<String> getCartItemMaterials() {
        List<String> materials = new ArrayList<>();
        Locator materialElements = page.locator(CART_ITEM_MATERIAL);
        int count = materialElements.count();
        for (int i = 0; i < count; i++) {
            String text = materialElements.nth(i).innerText().trim();
            if (text.toLowerCase().contains("material")) {
                materials.add(text);
            }
        }
        return materials;
    }

    public boolean isMaterialPresentInCart(String material) {
        Locator materialText = page.locator(
                String.format("text='Material: %s', .cart-item:has-text('%s'), " +
                              "[class*='cart-item']:has-text('Material: %s')",
                        material, material, material)
        );
        return materialText.count() > 0;
    }

    public boolean isCartVisible() {
        Locator cartDrawer = page.locator(CART_DRAWER).first();
        return cartDrawer.isVisible();
    }

    /**
     * Prints cart item details from the cart drawer.
     * Extracts name, price, and material for each item.
     */
    public void printCartItemsToConsole() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("🛒 CART ITEMS FROM CART DRAWER");
        System.out.println("=".repeat(80));

        Locator cartItems = page.locator(CART_ITEMS);
        int count = cartItems.count();

        for (int i = 0; i < count; i++) {
            Locator item = cartItems.nth(i);
            String itemText = item.innerText();
            System.out.printf("--- Item %d ---%n%s%n", i + 1, itemText.trim());
            System.out.println();
        }
        System.out.println("=".repeat(80) + "\n");
    }
}
