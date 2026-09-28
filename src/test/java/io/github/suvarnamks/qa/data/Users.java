package io.github.suvarnamks.qa.data;

import org.testng.annotations.DataProvider;

public class Users {

    // public demo password shown on the saucedemo login page
    public static final String PASSWORD = "secret_sauce";
    public static final String STANDARD = "standard_user";

    @DataProvider(name = "invalidLogins", parallel = true)
    public static Object[][] invalidLogins() {
        return new Object[][] {
            {"", PASSWORD, "Username is required"},
            {STANDARD, "", "Password is required"},
            {STANDARD, "wrong", "Username and password do not match"},
            {"locked_out_user", PASSWORD, "Sorry, this user has been locked out."},
        };
    }
}
