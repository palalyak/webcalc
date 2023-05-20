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


@Epic("UI Verify calculator input")
@Feature("Click calc buttons")
@Listeners(com.lista.automation.api.TestListener.class)
public class CalBasicFormulasInputTest extends BaseTest {

    @Test(dataProvider = "getData")
    public void testEnteringKeyboardCharacters(HashMap<String, String> input) {
        step("Enter keyboard characters", () -> {
            webCalcPage.enterCharacters(input.get("formula")).submit();

            step("UI: assert", () -> {
                assertThat(webCalcPage.getCalculationResult()).isEqualTo(input.get("result"));
            });
        });
    }

    @Test(dataProvider = "getDataNegative")
    public void testEnteringKeyboardNumbersNegative(HashMap<String, String> input) {
        step("Enter keyboard characters", () -> {
            webCalcPage.enterCharacters(input.get("formula")).submit();

            step("UI: assert", () -> {
                assertThat(webCalcPage.getCalculationResult()).isNotEqualTo(input.get("result"));
            });
        });
    }











    @DataProvider
    public Object[][] getData() throws IOException {
        List<HashMap<String, String>> data = getJsonData("//src//test//java//com//webcalc//ui//core//utils//calcData.json");
        Object[][] testData = new Object[data.size()][1];
        for (int i = 0; i < data.size(); i++) {
            testData[i][0] = data.get(i);
        }
        return testData;
    }

    @DataProvider
    public Object[][] getDataNegative() throws IOException {
        List<HashMap<String, String>> data = getJsonData("//src//test//java//com//webcalc//ui//core//utils//calcDataNegative.json");
        Object[][] testData = new Object[data.size()][1];
        for (int i = 0; i < data.size(); i++) {
            testData[i][0] = data.get(i);
        }
        return testData;
    }
}
