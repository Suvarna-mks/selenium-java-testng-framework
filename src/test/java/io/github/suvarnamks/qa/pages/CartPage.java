package io.github.suvarnamks.qa.pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage {

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public List<String> itemNames() {
        waitForUrl("cart.html");
        // only look inside the cart list: for a moment after navigation the old product list is still in the page
        return texts(By.cssSelector(".cart_list [data-test='inventory-item-name']"));
    }

    public CheckoutPage checkout() {
        waitForUrl("cart.html");
        click(testId("checkout"));
        waitForUrl("checkout-step-one.html");
        return new CheckoutPage(driver);
    }
}
