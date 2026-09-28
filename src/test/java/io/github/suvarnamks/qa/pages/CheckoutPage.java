package io.github.suvarnamks.qa.pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends BasePage {

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public CheckoutPage fillInfo(String first, String last, String postalCode) {
        type(testId("firstName"), first);
        type(testId("lastName"), last);
        type(testId("postalCode"), postalCode);
        click(testId("continue"));
        return this;
    }

    public List<Double> itemPrices() {
        waitForUrl("checkout-step-two.html");
        return texts(By.cssSelector(".cart_list [data-test='inventory-item-price']")).stream().map(CheckoutPage::money).toList();
    }

    public double subtotal() { return money(text(testId("subtotal-label"))); }
    public double tax() { return money(text(testId("tax-label"))); }
    public double total() { return money(text(testId("total-label"))); }

    public String error() {
        return text(testId("error"));
    }

    public String finish() {
        click(testId("finish"));
        return text(testId("complete-header"));
    }

    // "Item total: $39.98" -> 39.98
    private static double money(String label) {
        return Double.parseDouble(label.substring(label.indexOf('$') + 1));
    }
}
