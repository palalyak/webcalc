package com.webcalc.ui.core.utils;
import java.util.Properties;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class AllureEnvironmentWriter {
    private static final String ALLURE_RESULTS_PATH = "target/allure-results/";
    private static final String ALLURE_ENV_PROP_PATH = "allure-results/environment.properties";

    public static void createEnvironmentPropertiesFile() {
        Properties properties = new Properties();

        createAllureResultsDirectoryIfNotExists();

        try (OutputStream outputStream = new FileOutputStream(ALLURE_ENV_PROP_PATH)) {
            properties.setProperty("Browser", "Chrome");
            properties.setProperty("Browser Version", "101");
            properties.setProperty("OS", System.getProperty("os.name"));
            properties.store(outputStream, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createAllureResultsDirectoryIfNotExists() {
        new File(ALLURE_RESULTS_PATH).mkdir();
    }
}
