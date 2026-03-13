package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.junit.jupiter.api.Assertions;
import java.util.ArrayList;
import java.util.List;

public class ProductDetailsPage {

    private final Page page;
    private String productUrl;
    private final List<CartItemDetail> cartItems = new ArrayList<>();

    public static class CartItemDetail {
        public String material;
        public String price;
        public String link;

        public CartItemDetail(String material, String price, String link) {
            this.material = material;
            this.price = price;
            this.link = link;
        }
    }

    public ProductDetailsPage(Page page) {
        this.page = page;
    }

    public boolean isMaterialOptionVisible(String material) {
        productUrl = page.url();
        page.waitForTimeout(1500);
        Locator opt = page.locator(
            "label:has-text('" + material + "'), " +
            "input[value='" + material + "'], " +
            ".variant-pills__option:has-text('" + material + "'), " +
            "button:has-text('" + material + "')"
        ).first();
        return opt.count() > 0 && opt.isVisible();
    }

    public void selectMaterialAndAddToCart(String material) {
        productUrl = page.url();
        String price = "";

        Locator opt = page.locator(
            "label:has-text('" + material + "'), " +
            "input[value='" + material + "'], " +
            ".variant-pills__option:has-text('" + material + "'), " +
            "button:has-text('" + material + "')"
        ).first();
        opt.click();
        page.waitForTimeout(800);

        Locator priceEl = page.locator(
            ".price__regular .price-item, .price .price-item--regular, .product__price"
        ).first();
        if (priceEl.count() > 0) price = priceEl.innerText().trim();

        cartItems.add(new CartItemDetail(material, price, productUrl));

        Locator addBtn = page.locator(
            "button:has-text('Add to cart'), button[name='add'], button.product-form__submit"
        ).first();
        addBtn.waitFor();
        addBtn.click();
        page.waitForTimeout(2000);
        System.out.println("✅ Added " + material + " | Price: " + price + " | URL: " + productUrl);
    }

    public void goBackToProductOptions() {
        if (productUrl != null) {
            page.navigate(productUrl);
            page.waitForLoadState();
            page.waitForTimeout(2000);
            System.out.println("✅ Back to product page: " + page.url());
        }
    }

    public List<CartItemDetail> getCartItemDetails() {
        return cartItems;
    }

    public void printAllCartItemDetails() {
        System.out.println("\n========== CART ITEM DETAILS ==========");
        for (CartItemDetail item : cartItems) {
            System.out.println("Material : " + item.material);
            System.out.println("Price    : " + item.price);
            System.out.println("Link     : " + item.link);
            System.out.println("---------------------------------------");
        }
    }
}