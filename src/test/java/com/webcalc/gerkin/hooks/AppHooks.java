package com.webcalc.gerkin.hooks;

import com.webcalc.gerkin.factory.DriverFactory;
import com.webcalc.ui.core.utils.Properties;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class AppHooks {

    private DriverFactory driverFactory;
    private WebDriver driver;

    @Before
    public void launchBrowser() {
        String browserName = Properties.getProp().browser();
        driverFactory = new DriverFactory();
        driver = driverFactory.init_driver(browserName);
    }

    @After(order = 0)
    public void tearDown() {
        driver.quit();
    }

    @After(order = 1)
    public void takeScreenshot(Scenario scenario) {
        if(scenario.isFailed()) {
            String screenshotName = scenario.getName().replaceAll(" ", "_");
            byte[] sourcePath = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(sourcePath, "image/png", screenshotName);
        }
    }

}
