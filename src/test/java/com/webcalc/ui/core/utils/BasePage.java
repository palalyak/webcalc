package com.webcalc.ui.core.utils;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;

import java.io.ByteArrayInputStream;
import java.nio.file.Paths;
import java.util.regex.Pattern;

public class BasePage {
    private Page page;

    public BasePage(Page page) {
        this.page = page;
    }

    public void clickBy(String selector,int delay, boolean request) {
        attachAllureLog("click on element: ", selector,"");

        if(request) {
            System.out.println(">> click on " + selector + " and wait for response OK <<");
            page.waitForResponse(Response::ok, () -> {
                page.locator(selector).click(new Locator.ClickOptions().setDelay(delay));
            });
        }
        else {
            System.out.println(">> click on " + selector + " <<");
            page.locator(selector).click(new Locator.ClickOptions().setDelay(delay));
        }
    }

    @Step("extract JS value")
    public String extractJSValue(String selector) {
        attachAllureLog("extract JS value: ", selector,"");
        return page.evaluate("() => document.querySelector('"+selector+"').value").toString();
    }

    public void clickBy(Locator locator, int delay, boolean request) {
        attachAllureLog("click on element and wait 30000ms for response OK",locator,"");
        locator.scrollIntoViewIfNeeded();

        if(request) {
            System.out.println(">> click on " + locator + " and wait for response OK <<");
            page.waitForResponse(Response::ok, () -> {
                locator.click(new Locator.ClickOptions().setDelay(delay));
            });
        }
        else {
            System.out.println(">> click on " + locator + " <<");
            locator.click(new Locator.ClickOptions().setDelay(delay));
        }
    }

    public void typeIn(String selector, String text) {
        page.click(selector);
        page.locator(selector).clear();
        page.locator(selector).fill(text);
    }

    public Locator getLocator(String path) {
        return page.locator(path);
    }

    public String getInnerTextBy(String path) {
        return page.locator(path).innerText().toLowerCase().trim();
    }

    public void waitForTimeout(int msec) {
        Allure.addAttachment("wait msec", String.valueOf(msec));
        page.waitForTimeout(msec);
    }

    public Locator hasText(String selector, String text) {
       return getLocator(selector)
                .filter(new Locator.FilterOptions().setHasText(Pattern.compile(text)));
    }

    public boolean isVisible(String selector) {
        return page.locator(selector).isVisible();
    }

    private void attachAllureLog(String description, Locator locator, String text) {
        Allure.addAttachment(description, "locator/text: " + locator.toString()+"|"+text);

        byte[] screenshot = locator.screenshot(new Locator.ScreenshotOptions()
                .setPath(Paths.get(Properties.getProp().screenshotPath()+"screenshot.png")));
        Allure.addAttachment("element screenshot", new ByteArrayInputStream(screenshot));
    }
    private void attachAllureLog(String description, String selector, String text) {
        Allure.addAttachment(description, "selector/text: " + selector+"|"+text);

        byte[] screenshot = page.locator(selector).screenshot(new Locator.ScreenshotOptions()
                .setPath(Paths.get(Properties.getProp().screenshotPath()+"screenshot.png")));
        Allure.addAttachment("element screenshot", new ByteArrayInputStream(screenshot));
    }

}
