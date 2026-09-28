package io.github.suvarnamks.qa.pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class InventoryPage extends BasePage {

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public String title() {
        waitForUrl("inventory.html");
        return text(testId("title"));
    }

    public int itemCount() {
        visible(testId("inventory-item"));
        return driver.findElements(testId("inventory-item")).size();
    }

    public void sortBy(String value) {
        new Select(visible(testId("product-sort-container"))).selectByValue(value);
    }

    public List<Double> prices() {
        return texts(testId("inventory-item-price")).stream()
                .map(t -> Double.parseDouble(t.replace("$", "")))
                .toList();
    }

    public List<String> names() {
        return texts(testId("inventory-item-name"));
    }

    // "Sauce Labs Backpack" -> add-to-cart-sauce-labs-backpack
    public InventoryPage addToCart(String productName) {
        String slug = productName.toLowerCase().replace(" ", "-");
        waitForUrl("inventory.html");
        click(testId("add-to-cart-" + slug));
        // wait for the result of the click, not just the click: the button turns into "Remove"
        visible(testId("remove-" + slug));
        return this;
    }

    public String cartBadge() {
        return text(testId("shopping-cart-badge"));
    }

    public CartPage openCart() {
        click(testId("shopping-cart-link"));
        return new CartPage(driver);
    }

    public boolean isOnPage() {
        return driver.getCurrentUrl().contains("inventory.html") && !driver.findElements(By.cssSelector(".inventory_list")).isEmpty();
    }
}
