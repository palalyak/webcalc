package com.webcalc.gerkin.testrunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = {"src/test/resources/appfeatures"},
        glue = {"stepdefinitions", "AppHooks"},
        tags = "@Regression",
        plugin = {"pretty",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
                "html:target/cucumber-report/cucumber.xml"},
        dryRun = true

)
public class CucumberRunnerTests extends AbstractTestNGCucumberTests {


}
