package com.webcalc.ui.core;

import com.webcalc.ui.core.utils.Properties;
import com.microsoft.playwright.*;
import io.qameta.allure.Allure;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

public class PlaywrightFactory {
    private final String videoPath = Properties.getProp().videoPath();
    private final String networkPath = Properties.getProp().networkPath();
    private final String screenPath = Properties.getProp().screenshotPath();
    private final String tracePath = Properties.getProp().tracePath();
    private final double randomChar = Math.random();
    private static ThreadLocal<Playwright> tlPlaywright = new ThreadLocal<>();
    private static ThreadLocal<Browser> tlBrowser = new ThreadLocal<>();
    private static ThreadLocal<BrowserContext> tlContext = new ThreadLocal<>();
    private static ThreadLocal<Page> tlPage = new ThreadLocal<>();


    public static Playwright getTlPlaywright() {
        return tlPlaywright.get();
    }
    public static Browser getTlBrowser() {
        return tlBrowser.get();
    }
    public static BrowserContext getTlContext() {
        return tlContext.get();
    }

    public static Page getTlPage() {
        return tlPage.get();
    }

    public Playwright playwrightCreate() {
        tlPlaywright.set(Playwright.create());
        return getTlPlaywright();
    }

    public Browser startBrowser() {
        tlBrowser.set(getTlPlaywright().chromium().launch(new BrowserType.LaunchOptions()
                .setSlowMo(Properties.getProp().sloMotion())
                .setHeadless(Properties.getProp().mode())));
        return getTlBrowser();
    }

    public BrowserContext startContext() {
        tlContext.set(getTlBrowser().newContext(new Browser.NewContextOptions()
                .setLocale(Properties.getProp().local())
                .setRecordHarPath(Paths.get(networkPath + randomChar + ".har"))
                .setRecordVideoDir(Paths.get(videoPath))
                .setViewportSize(Properties.getProp().viewportWidth(), Properties.getProp().viewportHeight())
                .setTimezoneId(Properties.getProp().timeZone())
                .setGeolocation(Properties.getProp().latitude(), Properties.getProp().longitude())
                .setPermissions(Collections.singletonList("notifications"))));

        getTlContext().tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));
        return getTlContext();
    }

    public Page startPage() {
        tlPage.set(getTlContext().newPage());
        getTlPage().navigate(Properties.getProp().baseURL());
        return getTlPage();
    }

    public void contextStop(Method testInfo, ITestResult iTestResult) throws IOException, InterruptedException {
        String videoName = getTlPage().video().path().getFileName().toString();
        String logName = getLogName(testInfo);
        String tracePathStr = tracePath + logName + ".zip";
        String screenPathStr = screenPath + logName + ".png";
        Path zipFilePath = getPath(tracePathStr);
        Path screenFilePath = getPath(screenPathStr);

        Path networkFilePath = getPath(networkPath + randomChar + ".har");
        Path videoFilePath = getPath(videoPath + videoName);

        getTlContext().tracing().stop(new Tracing.StopOptions()
                .setPath(zipFilePath));

        Thread.sleep(10000);
        byte[] screenshot = getTlPage().screenshot(new Page.ScreenshotOptions()
                .setPath(screenFilePath).setFullPage(true));
        getTlContext().close();
        Allure.addAttachment(">> context closed <<",".txt","");


        byte[] videoContents = Files.readAllBytes(videoFilePath);
        byte[] zipContents = Files.readAllBytes(zipFilePath);
        byte[] networkContents = Files.readAllBytes(networkFilePath);

        if (iTestResult.getStatus() == 2) {
            Allure.addAttachment("SCREENSHOT_" + logName,
                    new ByteArrayInputStream(screenshot));

            Allure.addAttachment("TRACE_" + logName,
                    new ByteArrayInputStream(zipContents));

            Allure.addAttachment("NETWORK_" + logName,
                    new ByteArrayInputStream(networkContents));

            Allure.addAttachment("VIDEO_" + logName,
                    new ByteArrayInputStream(videoContents));
        }
    }

    private String getLogName(Method testInfo) {
        String formattedDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern(Properties.getProp().dateTimePattern()));
        return String.format("%s_%s", formattedDateTime, testInfo.getName());
    }

    public void playwrightStop() {
        getTlBrowser().close();
        getTlPlaywright().close();


    }

    private Path getPath(String customPath) {
        return Paths.get(customPath);
    }

}
