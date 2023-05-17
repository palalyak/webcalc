package com.webcalc.ui.core.utils;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;

import java.io.ByteArrayInputStream;
import java.nio.file.Paths;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

public class BasePage {
    private Page page;

    public BasePage(Page page) {
        this.page = page;
    }

    public int getRandomInt(int from, int to) {
        return ThreadLocalRandom.current()
                .nextInt(from, to);
    }

    public Locator getByRoleWithText(AriaRole role, String htmlText) {
        return page.getByRole(role, new Page.GetByRoleOptions()
                .setName(Pattern.compile(htmlText, Pattern.CASE_INSENSITIVE)).setExact(true));
    }

    public Locator getByRole(AriaRole role) {
        return page.getByRole(role);
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

    public void dblClickBy(Locator locator, boolean request) {
        attachAllureLog("double click on element and wait 30000ms for response OK", locator,"");
        locator.scrollIntoViewIfNeeded();

        if(request) {
            System.out.println(">> click on " + locator + " and wait for response OK <<");
            page.waitForResponse(Response::ok, locator::dblclick);
        }
        else {
            System.out.println(">> click on " + locator + " <<");
            locator.dblclick();
        }
    }

    public void clickWithCoordinate(String selector, int x, int y) {
            page.locator(selector).click(new Locator.ClickOptions().setPosition(x, y));
    }

    public void typeIn(String selector, String text) {
        page.click(selector);
        page.locator(selector).clear();
        page.locator(selector).fill(text);
    }

    public void typeIn(Locator locator, String text) {
        locator.click();
        locator.clear();
        locator.fill(text);
    }

    public void waitForLoadState() {
        page.waitForLoadState(LoadState.NETWORKIDLE);
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

    public Locator getByText(String text) {
        Locator locator = page.getByText(Pattern.compile(".*"+text+".*", Pattern.CASE_INSENSITIVE));
        locator.scrollIntoViewIfNeeded();
        return locator;
    }
    public Locator getByExactText(String text) {
        Locator locator = page.getByText(text, new Page.GetByTextOptions().setExact(true));
        locator.scrollIntoViewIfNeeded();
        return locator;
    }

    public Locator getByPlaceholder(String text) {
        return page.getByPlaceholder(text);
    }

    public boolean isVisible(String selector) {
        return page.locator(selector).isVisible();
    }
    public boolean isVisible(Locator locator) {
        return locator.isVisible();
    }
    public String getAttribute(String selector, String attr) {
        return page.locator(selector).getAttribute(attr);
    }

    public static String getMethodNaming() {
        return Thread.currentThread().getStackTrace()[1].getMethodName();
    }

    public void waitForURL(String regex) {
        Allure.addAttachment("wait for URL", regex);
        System.out.println("wait for URL: " + regex);
        page.waitForURL(Pattern.compile(regex));
    }

    public int countElements(String selector) {
        Allure.addAttachment("count element", selector);
        return page.locator(selector).count();
    }

    public void clearCookies() {
        page.context().clearCookies();
        page.reload();
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
