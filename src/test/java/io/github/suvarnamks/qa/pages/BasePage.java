package io.github.suvarnamks.qa.pages;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage {

    protected final WebDriver driver;
    private final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // saucedemo puts data-test="..." on its elements
    protected By testId(String id) {
        return By.cssSelector("[data-test='" + id + "']");
    }

    // explicit waits only, no Thread.sleep anywhere
    protected WebElement visible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // use before reading elements that also exist on the previous page
    protected void waitForUrl(String part) {
        wait.until(ExpectedConditions.urlContains(part));
    }

    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    protected void type(By locator, String text) {
        WebElement el = visible(locator);
        el.clear();
        el.sendKeys(text);
    }

    // read the text of every match; if the page re-renders mid-read (stale element), try again
    protected List<String> texts(By locator) {
        return wait.ignoring(StaleElementReferenceException.class).until(d -> {
            List<String> values = d.findElements(locator).stream().map(WebElement::getText).toList();
            return values.isEmpty() ? null : values;
        });
    }

    protected String text(By locator) {
        return visible(locator).getText();
    }
}
