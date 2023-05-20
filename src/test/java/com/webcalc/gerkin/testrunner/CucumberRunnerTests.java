package com.webcalc.gerkin.testrunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = {"src/test/resources/appfeatures"},
        glue = {"com.webcalc.gerkin.stepdefinitions", "com.webcalc.gerkin.hooks"},
        tags = "@Regression",
        plugin = {"pretty",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
                "html:target/cucumber-report/cucumber.xml"}

)
public class CucumberRunnerTests extends AbstractTestNGCucumberTests {


}
