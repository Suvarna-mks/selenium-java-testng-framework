package io.github.suvarnamks.qa.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import io.github.suvarnamks.qa.base.BaseTest;
import io.github.suvarnamks.qa.data.Users;
import io.github.suvarnamks.qa.pages.CartPage;
import io.github.suvarnamks.qa.pages.CheckoutPage;
import io.github.suvarnamks.qa.pages.LoginPage;
import java.util.List;
import org.testng.annotations.Test;

public class CheckoutTest extends BaseTest {

    @Test(groups = "smoke")
    public void buyTwoItemsTotalsAreCorrect() {
        CartPage cart = new LoginPage(driver()).loginAs(Users.STANDARD, Users.PASSWORD)
                .addToCart("Sauce Labs Backpack")
                .addToCart("Sauce Labs Fleece Jacket")
                .openCart();
        assertEquals(cart.itemNames(), List.of("Sauce Labs Backpack", "Sauce Labs Fleece Jacket"));

        CheckoutPage checkout = cart.checkout().fillInfo("Test", "User", "560001");
        double itemSum = checkout.itemPrices().stream().mapToDouble(Double::doubleValue).sum();
        double expectedTax = Math.round(itemSum * 0.08 * 100) / 100.0; // site charges 8% tax

        assertEquals(checkout.subtotal(), itemSum, 0.001);
        assertEquals(checkout.tax(), expectedTax, 0.001);
        assertEquals(checkout.total(), itemSum + expectedTax, 0.001);
        assertEquals(checkout.finish(), "Thank you for your order!");
    }

    @Test
    public void postalCodeIsRequired() {
        CheckoutPage checkout = new LoginPage(driver()).loginAs(Users.STANDARD, Users.PASSWORD)
                .addToCart("Sauce Labs Onesie").openCart().checkout()
                .fillInfo("Test", "User", "");
        assertTrue(checkout.error().contains("Postal Code is required"));
    }
}
