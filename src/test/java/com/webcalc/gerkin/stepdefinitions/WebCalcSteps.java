package com.webcalc.gerkin.stepdefinitions;

import com.webcalc.api.core.RestWrapper;
import com.webcalc.gerkin.factory.DriverFactory;
import com.webcalc.gerkin.pages.WebCalcPage;
import com.webcalc.ui.core.keyoptions.BtnCalc;
import com.webcalc.ui.core.keyoptions.CalcTypes;
import com.webcalc.ui.core.utils.Properties;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;

import java.util.List;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

@Epic("Cucumber UI tests")
@Feature("Click calc buttons")
public class WebCalcSteps {
    private WebCalcPage webCalcPage = new WebCalcPage(DriverFactory.getDriver());
    private RestWrapper api = DriverFactory.getApi();
    private String result;
    @Given("user navigate to WebCalculator site")
    public void userNavigateToWebCalculatorSite() {
        DriverFactory.getDriver().get(Properties.getProp().baseURL());
        webCalcPage.closePopup();
    }
    @Given("calculator is opened")
    public void calculatorIsOpened() {
        assertThat(webCalcPage.getCalculator().size()).as("calculator appear").isGreaterThan(0);
    }
    @When("user sends an api request with a function")
    public void userSendsAnApiRequestWithAFunction() {
        result = api.calcService().calc("2+3");
    }

    @Then("api response contains the correct calculation")
    public void apiResponseContainsTheCorrectCalculation() {
        assertThat(result).isEqualTo("5");
    }

    @When("user change calculator type to {}")
    public void userChangeCalculatorType(CalcTypes calcTypes) {
        String activeType = webCalcPage.currentActiveCalcType();

        if (!activeType.equals(calcTypes.getValue())) {
            step("open helper menu", () -> {
                webCalcPage.openMenu();
            });
            step("change calculator type", () -> {
                webCalcPage.setCalcType(calcTypes.getValue());
            });
        }
    }

    @Then("calculator title should be {string}")
    public void calculatorTitleShouldBeScientific(String title) {
        assertThat(webCalcPage.currentActiveCalcType()).isEqualTo(title);
    }


    @When("user clicks the following buttons")
    public void userClicksTheFollowingButtons(DataTable dataTable) {
        List<List<String>> rows = dataTable.asLists(String.class);
        for (List<String> row : rows) {
            for (String column : row) {
                BtnCalc button = BtnCalc.valueOf(column);
                webCalcPage.clickCalcButtons(button.getValue());
            }
        }

    }

    @And("user submit calculation by clicking {string}")
    public void userSubmitCalculationBy(String btnCalc) {
        BtnCalc button = BtnCalc.valueOf(btnCalc);
        webCalcPage.clickCalcButtons(button.getValue());
        webCalcPage.waitTimer(2000);
    }

    @Then("result of calculation should be {string}")
    public void resultOfCalculationShouldBe(String arg0) {
        assertThat(webCalcPage.extractJSValue("#input")).isEqualTo(arg0);
    }

    @Then("result of calculation is not should be {string}")
    public void resultOfCalculationIsNotShouldBe(String arg0) {
        assertThat(webCalcPage.extractJSValue("#input")).isNotEqualTo(arg0);
    }

    @Then("history of calculation contains formula {string} and result {string}")
    public void historyOfCalculationContainsFormulaAndResult(String formula, String expectedResult) {
        assertThat(webCalcPage.getHistoryOfResults()).containsEntry(formula, expectedResult);

    }
}
