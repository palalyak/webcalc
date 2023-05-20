package com.webcalc.gerkin.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class WebCalcPage extends SeleniumHelper {

    @FindBy(css = ".modal-content button[value='consent']")
    WebElement popupEl;
    @FindBy(css = "#histframe > ul > li")
    List<WebElement> historyLinesEl;

    private WebDriver driver;
    private String helperMenu = "#inputhelper";
    private String popup = ".modal-content button[value='consent']";
    private String currentActiveCalcType = "#inputhelpermenu li[class=active] a";


    public WebCalcPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public List<WebElement> getCalculator() {
        return findListBy(By.cssSelector("#calccontainer"));
    }

    public String currentActiveCalcType() {
        waitTimer(2000);
        return getTextBy(By.cssSelector(currentActiveCalcType));
    }

    public void openMenu() {
        clickBy(By.cssSelector(helperMenu));
    }

    public void setCalcType(String calcTypes) {
        findListBy(By.cssSelector("#inputhelpermenu a"))
                .stream()
                .filter(item -> item.getText().equals(calcTypes))
                .findFirst()
                .ifPresent(WebElement::click);
    }

    public Map<String, String> getHistoryOfResults() {
        clickBy(By.cssSelector("#hist"));
        return historyLinesEl.stream()
                .collect(Collectors.toMap(
                        liElement -> liElement.findElement(By.cssSelector("p.l")).getText(),
                        liElement -> liElement.findElement(By.cssSelector("p.r")).getText()
                                .replaceAll("[\\s=\\u00A0]", ""),
                        (value1, value2) -> value2
                ));
    }

    public void clickCalcButtons(String btnCalc) {
        clickBy(By.cssSelector(btnCalc));
    }

    public String extractJSValue(String selector) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        return jsExecutor.executeScript(
                "return document.querySelector('" + selector + "').value"
        ).toString();
    }

    public void closePopup() {
        waitTimer(2000);
        if (isElementVisible(popupEl)) {
            clickBy(By.cssSelector(popup));
        }
        assertThat(!isElementVisible(popupEl)).as("popup is closed").isTrue();

    }

    public void waitTimer(int msec) {
        try {
            Thread.sleep(msec);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
