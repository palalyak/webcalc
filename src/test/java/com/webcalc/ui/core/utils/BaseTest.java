package com.webcalc.ui.core.utils;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Playwright;
import com.webcalc.api.core.RestService;
import com.webcalc.api.core.RestWrapper;
import com.webcalc.ui.core.PlaywrightFactory;
import com.webcalc.ui.pages.WebCalc;
import com.microsoft.playwright.Page;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;
import java.lang.reflect.Method;

public class BaseTest {

    private PlaywrightFactory pf;
    private Page page;
    private Playwright playwright;
    private BrowserContext context;
    private Browser browser;
    protected WebCalc webCalc;

    protected RestWrapper api;


    @BeforeTest
    public void setFilter() {
        RestAssured.filters(new AllureRestAssured());
    }

//    @BeforeSuite
//    public void setUp() {
//        pf = new PlaywrightFactory();
//        page = pf.initBrowser();
//        webCalc = new WebCalc(page);
//        webCalc.closePopup();
////        api = RestWrapper.loginAs(permissionLevel);
//
//    }
//
//    @AfterSuite
//    public void tearDown(Method testInfo, ITestResult iTestResult) throws IOException {
//        pf.stop(testInfo, iTestResult);
//
//    }

    @BeforeClass
    public void launchBrowser() {
        api = RestWrapper.request();
        pf = new PlaywrightFactory();
        playwright = pf.playCreate();
        browser = pf.startBrowser();
        context = pf.startContext();
    }

    @BeforeMethod
    public void createContextAndPage() {
        page = pf.startPage();
        webCalc = new WebCalc(page);
        webCalc.closePopup();
    }

    @AfterClass
    public void closeBrowser() throws IOException, InterruptedException {
        pf.contextStop();
        pf.playwrightStop();
    }


}
