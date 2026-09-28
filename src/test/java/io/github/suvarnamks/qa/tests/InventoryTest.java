package io.github.suvarnamks.qa.tests;

import static org.testng.Assert.assertEquals;

import io.github.suvarnamks.qa.base.BaseTest;
import io.github.suvarnamks.qa.data.Users;
import io.github.suvarnamks.qa.pages.InventoryPage;
import io.github.suvarnamks.qa.pages.LoginPage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.testng.annotations.Test;

public class InventoryTest extends BaseTest {

    private InventoryPage login() {
        return new LoginPage(driver()).loginAs(Users.STANDARD, Users.PASSWORD);
    }

    @Test(groups = "smoke")
    public void showsSixProducts() {
        assertEquals(login().itemCount(), 6);
    }

    @Test
    public void sortByPriceLowToHigh() {
        InventoryPage inventory = login();
        inventory.sortBy("lohi");
        List<Double> expected = new ArrayList<>(inventory.prices());
        Collections.sort(expected);
        assertEquals(inventory.prices(), expected);
    }

    @Test
    public void sortByNameZToA() {
        InventoryPage inventory = login();
        inventory.sortBy("za");
        List<String> expected = new ArrayList<>(inventory.names());
        expected.sort(Comparator.reverseOrder());
        assertEquals(inventory.names(), expected);
    }

    @Test
    public void cartBadgeCountsItems() {
        InventoryPage inventory = login().addToCart("Sauce Labs Backpack").addToCart("Sauce Labs Bike Light");
        assertEquals(inventory.cartBadge(), "2");
    }
}
