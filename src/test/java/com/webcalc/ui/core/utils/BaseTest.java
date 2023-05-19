package com.webcalc.ui.core.utils;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Playwright;
import com.webcalc.api.core.RestWrapper;
import com.webcalc.ui.core.PlaywrightFactory;
import com.webcalc.ui.pages.WebCalcPage;
import com.microsoft.playwright.Page;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;
import java.lang.reflect.Method;

import static com.webcalc.ui.core.utils.AllureEnvironmentWriter.createEnvironmentPropertiesFile;

public class BaseTest {

    private PlaywrightFactory pf;
    private Page page;
    private Playwright playwright;
    private BrowserContext context;
    private Browser browser;
    protected WebCalcPage webCalcPage;

    protected RestWrapper api;

    @BeforeSuite
    public void setUpEnvironment() {
        createEnvironmentPropertiesFile();
    }

    @BeforeTest
    public void setFilter() {
        RestAssured.filters(new AllureRestAssured());
    }


    @BeforeClass
    public void launchBrowser() {
        api = RestWrapper.request();
        pf = new PlaywrightFactory();
        playwright = pf.playwrightCreate();
        browser = pf.startBrowser();
        context = pf.startContext();
    }

    @BeforeMethod
    public void createContextAndPage() {
        page = pf.startPage();
        webCalcPage = new WebCalcPage(page);
        webCalcPage.closePopup();
    }

    @AfterClass
    public void closeBrowser(Method testInfo) throws IOException, InterruptedException {
        pf.contextStop(testInfo);
        pf.playwrightStop();
    }


}
