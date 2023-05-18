package com.webcalc.ui.tests;

import com.webcalc.ui.core.utils.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static com.webcalc.ui.core.utils.JsonMapper.getJsonData;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

@Epic("Web calculator UI / Negative / Positive")
@Feature("Enter characters")
@Listeners(com.lista.automation.api.TestListener.class)
public class CalBasicFormulasInputTest extends BaseTest {

    @Test(dataProvider = "getData")
    public void testEnteringKeyboardCharacters(HashMap<String, String> input) {
        step("Enter keyboard characters", () -> {
            webCalc.enterCharacters(input.get("formula")).submit();

            step("UI: assert", () -> {
                assertThat(webCalc.getCalculationResult()).isEqualTo(input.get("result"));
            });
        });
    }

    @Test(dataProvider = "getDataNegative")
    public void testEnteringKeyboardNumbersNegative(HashMap<String, String> input) {
        step("Enter keyboard characters", () -> {
            webCalc.enterCharacters(input.get("formula")).submit();

            step("UI: assert", () -> {
                assertThat(webCalc.getCalculationResult()).isNotEqualTo(input.get("result"));
            });
        });
    }

    @DataProvider
    public Object[][] getData() throws IOException {
        List<HashMap<String, String>> data = getJsonData("//src//test//java//com//webcalc//ui//core//utils//calcData.json");
        return new Object[][]{{data.get(0)}, {data.get(1)}};
    }

    @DataProvider
    public Object[][] getDataNegative() throws IOException {
        List<HashMap<String, String>> data = getJsonData("//src//test//java//com//webcalc//ui//core//utils//calcDataNegative.json");
        return new Object[][]{{data.get(0)}, {data.get(1)}};
    }
}
