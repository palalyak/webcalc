package com.webcalc.ui.tests;

import com.webcalc.ui.core.keyoptions.CalcTypes;
import com.webcalc.ui.core.utils.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.Arrays;

import static com.webcalc.ui.core.keyoptions.BtnCalc.*;
import static com.webcalc.ui.core.keyoptions.CalcTypes.*;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

@Epic("Web calculator UI / Basic math operations")
@Feature("Click calc buttons")
@Listeners(com.lista.automation.api.TestListener.class)
public class CalBasicFormulasBtnTest extends BaseTest {

    @Test
    @Description("summation")
    public void testFormula1() {
        String formula = "2+3";
        String expectedResult = "5";

        step("UI operation: 2 + 3 = 5", () -> {
            webCalcPage.setTypeOfCalc(Scientific).enterCharacters(TWO, PLUS, THREE).submit(EQUALS);

            step("API: assert result", () -> {
                assertThat(api.calcService().calc("2+3")).isEqualTo(expectedResult);

                step("UI: assert result", () -> {
                    assertThat(webCalcPage.getCalculationResult()).isEqualTo(expectedResult);
                });
                step("UI: assert history", () -> {
                    assertThat(webCalcPage.getHistoryOfResults()).containsEntry(formula, expectedResult);
                    assertThat(webCalcPage.getHistoryEntries()).as("formulas do not repeat").isEmpty();
                });
            });
        });
    }

    @Test
    @Description("subtracting")
    public void testFormula2() {
        String formula = "10-8";
        String expectedResult = "2";

        step("UI operation: 10 - 8 = 2", () -> {
            webCalcPage.setTypeOfCalc(Scientific).enterCharacters(ONE, ZERO, MINUS, EIGHT).submit(EQUALS);

            step("API: assert result", () -> {
                assertThat(api.calcService().calc("10-8")).isEqualTo(expectedResult);

                step("UI: assert result", () -> {
                    assertThat(webCalcPage.getCalculationResult()).isEqualTo(expectedResult);
                });
                step("UI: assert history", () -> {
                    assertThat(webCalcPage.getHistoryOfResults()).containsEntry(formula, expectedResult);
                    assertThat(webCalcPage.getHistoryEntries()).as("formulas do not repeat").isEmpty();
                });
            });
        });
    }

    @Test
    @Description("sinus")
    public void testFormula3() {
        String formula = "sin(30)";
        String expectedResult = "0.5";

        step("UI operation: sin(30) = 0.5", () -> {
            webCalcPage.setTypeOfCalc(Scientific).enterCharacters(THREE, ZERO, SIN).submit(EQUALS);

            step("API: assert", () -> {
                assertThat(api.calcService().calc("sin(30)")).isEqualTo(expectedResult);

                step("UI: assert result", () -> {
                    assertThat(webCalcPage.getCalculationResult()).isEqualTo(expectedResult);
                });
                step("UI: assert history", () -> {
                    assertThat(webCalcPage.getHistoryOfResults()).containsEntry(formula, expectedResult);
                    assertThat(webCalcPage.getHistoryEntries()).as("formulas do not repeat").isEmpty();
                });
            });
        });
    }

    @Test
    @Description("multiplication")
    public void testFormula4() {
        String formula = "(10-2)*2";
        String expectedResult = "16";

        step("UI operation: (10 - 2) * 2 != 20", () -> {
            webCalcPage
                    .setTypeOfCalc(Scientific)
                    .enterCharacters(PARAN_L, ONE, ZERO, MINUS, TWO, PARAN_R, MULT, TWO)
                    .submit(EQUALS);

            step("API: assert result", () -> {
                assertThat(api.calcService().calc("(10-2)*2")).isEqualTo(expectedResult);

                step("UI: assert result", () -> {
                    assertThat(webCalcPage.getCalculationResult()).isNotEqualTo("20");
                });
                step("UI: assert history", () -> {
                    assertThat(webCalcPage.getHistoryOfResults()).containsEntry(formula, expectedResult);
                    assertThat(webCalcPage.getHistoryEntries()).as("formulas do not repeat").isEmpty();
                });
            });
        });
    }

    @Test(dataProvider = "getData")
    @Description("calc types")
    public void testCalculatorTypes(CalcTypes calcType) {
        webCalcPage.setTypeOfCalc(calcType);

        assertThat(webCalcPage.getInnerTextBy("#inputhelper .title"))
                .as("type was changed")
                .isEqualTo(calcType.getValue().toLowerCase());
    }






    @DataProvider
    public Object[][] getData() {
        return Arrays.stream(CalcTypes.values())
                .map(type -> new Object[] {type})
                .toArray(Object[][]::new);
    }
}
