package io.github.suvarnamks.qa.base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class BaseTest {

    protected static final String BASE_URL = System.getProperty("baseUrl", "https://www.saucedemo.com");

    protected WebDriver driver() {
        return DriverFactory.get();
    }

    @BeforeMethod(alwaysRun = true)
    public void startBrowser() {
        DriverFactory.create().get(BASE_URL);
    }

    @AfterMethod(alwaysRun = true)
    public void closeBrowser() {
        DriverFactory.quit();
    }
}
