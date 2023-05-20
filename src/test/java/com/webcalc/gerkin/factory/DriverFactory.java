package com.webcalc.gerkin.factory;

import com.webcalc.api.core.RestWrapper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import java.util.concurrent.TimeUnit;

public class DriverFactory {

    public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
    public WebDriver init_driver(String browser) {

        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);

        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                tlDriver.set(new ChromeDriver(options));
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                tlDriver.set(new FirefoxDriver());
                break;
            case "safari":
                tlDriver.set(new SafariDriver());
                break;
            default:
                System.out.println("Please pass the correct browser value: " + browser);
                break;
        }

        getDriver().manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        getDriver().manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        getDriver().manage().window().maximize();
        return getDriver();

    }

    public static synchronized WebDriver getDriver() {
        return tlDriver.get();
    }

    public static RestWrapper getApi() {
        return RestWrapper.request();
    }
}

