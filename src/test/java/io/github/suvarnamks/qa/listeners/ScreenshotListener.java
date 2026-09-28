package io.github.suvarnamks.qa.listeners;

import io.github.suvarnamks.qa.base.DriverFactory;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ScreenshotListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        WebDriver driver = DriverFactory.get();
        if (driver == null) return;
        try {
            Path dir = Path.of("target", "screenshots");
            Files.createDirectories(dir);
            Path file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE).toPath();
            Path target = dir.resolve(result.getMethod().getMethodName() + ".png");
            Files.copy(file, target, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Screenshot saved: " + target);
        } catch (IOException e) {
            System.out.println("Could not save screenshot: " + e.getMessage());
        }
    }
}
