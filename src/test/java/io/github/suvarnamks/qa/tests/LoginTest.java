package io.github.suvarnamks.qa.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import io.github.suvarnamks.qa.base.BaseTest;
import io.github.suvarnamks.qa.data.Users;
import io.github.suvarnamks.qa.pages.LoginPage;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test(groups = "smoke")
    public void standardUserCanLogIn() {
        var inventory = new LoginPage(driver()).loginAs(Users.STANDARD, Users.PASSWORD);
        assertEquals(inventory.title(), "Products");
    }

    @Test(dataProvider = "invalidLogins", dataProviderClass = Users.class)
    public void invalidLoginShowsError(String user, String password, String expectedError) {
        LoginPage login = new LoginPage(driver());
        login.loginAs(user, password);
        assertTrue(login.error().contains(expectedError), "error was: " + login.error());
    }
}
