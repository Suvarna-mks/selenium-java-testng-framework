package io.github.suvarnamks.qa.pages;

import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public InventoryPage loginAs(String username, String password) {
        type(testId("username"), username);
        type(testId("password"), password);
        click(testId("login-button"));
        return new InventoryPage(driver);
    }

    public String error() {
        return text(testId("error"));
    }
}
