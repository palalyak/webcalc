package com.webcalc.ui.tests;

import com.webcalc.ui.core.keyoptions.BtnCalc;
import com.webcalc.ui.core.utils.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import static com.webcalc.ui.core.keyoptions.BtnCalc.*;
import static com.webcalc.ui.core.keyoptions.CalcTypes.Programmer;
import static com.webcalc.ui.core.keyoptions.CalcTypes.Scientific;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

@Epic("Web calculator UI / Basic math operations")
@Feature("Click calc buttons")
@Listeners(com.lista.automation.api.TestListener.class)
public class CalBasicFormulasBtnTest extends BaseTest {

    @Test
    @Description("summation")
    public void testFormula1() {
        String expectedResult = "5";
        step("Formula: 2 + 3 = 5", () -> {
            webCalc.setTypeOfCalc(Programmer).enterCharacters(TWO, PLUS, THREE).submit(EQUALS);

            step("API: assert", () -> {
                assertThat(api.calcService().calc("2+3")).isEqualTo(expectedResult);

                step("UI: assert", () -> {
                    assertThat(webCalc.getCalculationResult()).isEqualTo(expectedResult);
                });
            });
        });
    }

    @Test
    @Description("subtracting")
    public void testFormula2() {
        String expectedResult = "2";
        step("Formula: 10 - 8 = 2", () -> {
            webCalc.setTypeOfCalc(Programmer).enterCharacters(ONE, ZERO, MINUS, EIGHT).submit(EQUALS);

            step("API: assert", () -> {
                assertThat(api.calcService().calc("10-8")).isEqualTo(expectedResult);

                step("UI: assert", () -> {
                    assertThat(webCalc.getCalculationResult()).isEqualTo(expectedResult);
                });
            });
        });
    }

    @Test
    @Description("sinus")
    public void testFormula3() {
        String expectedResult = "0.5";
        step("Formula: sin(30) = 0.5", () -> {
            webCalc.setTypeOfCalc(Scientific).enterCharacters(THREE, ZERO, SIN).submit(EQUALS);

            step("API: assert", () -> {
                assertThat(api.calcService().calc("sin(30)")).isEqualTo(expectedResult);

                step("UI: assert", () -> {
                    assertThat(webCalc.getCalculationResult()).isEqualTo(expectedResult);
                });
            });
        });
    }

    @Test
    @Description("multiplication")
    public void testFormula4() {
        String expectedResult = "16";
        step("Formula: (10 - 2) * 2 != 20", () -> {
            webCalc
                    .setTypeOfCalc(Scientific)
                    .enterCharacters(PARAN_L, ONE, ZERO, MINUS, TWO, PARAN_R, MULT, TWO)
                    .submit(EQUALS);

            step("API: assert", () -> {
                assertThat(api.calcService().calc("(10-2)*2")).isEqualTo(expectedResult);

                step("UI: assert", () -> {
                    assertThat(webCalc.getCalculationResult()).isNotEqualTo("20");
                });
            });
        });
    }

}
