package com.webcalc.gerkin.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;

public class SeleniumHelper {

    private WebDriver driver;
    private WebDriverWait wait;

    public SeleniumHelper(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement findBy(By by) {
        return driver.findElement(by);
    }

    public List<WebElement> findListBy(By by) {
        return driver.findElements(by);
    }

    public void clickBy(By by) {
        waitUntil("elementToBeClickable", findBy(by), 5, "");
        findBy(by).click();
    }
    public String getTextBy(By by) {
        waitUntil("visibilityOf", findBy(by), 5, "");
        return findBy(by).getText();
    }
    public WebElement waitUntil(String condition, WebElement el, int sec, String text) {
        wait = new WebDriverWait(driver, sec);
        switch (condition) {
            case "elementToBeClickable":
                wait.until(ExpectedConditions.elementToBeClickable(el));
                break;
            case "textToBePresentInElement":
                wait.until(ExpectedConditions.textToBePresentInElement(el, text));
                break;
            case "invisibilityOf":
                wait.until(ExpectedConditions.invisibilityOf(el));
                break;
            case "visibilityOf":
                return wait.until(ExpectedConditions.visibilityOf(el));
        }
        return el;
    }

    public boolean isElementVisible(WebElement el) {
        try {
            waitUntil("visibilityOf", el, 4, "");
            return true;
        } catch (org.openqa.selenium.NoSuchElementException | org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }
}
