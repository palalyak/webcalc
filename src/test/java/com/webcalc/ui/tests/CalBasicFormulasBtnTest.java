package com.webcalc.ui.tests;

import com.webcalc.ui.core.utils.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.webcalc.ui.core.keyoptions.BtnCalc.*;
import static com.webcalc.ui.core.keyoptions.CalcTypes.Programmer;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

@Epic("Web calculator UI")
@Feature("Basic math operations")
@Listeners(com.lista.automation.api.TestListener.class)
public class CalBasicFormulasBtnTest extends BaseTest {

    @Test
    public void testFormula1() {
        step("Formula: 2 + 3 = 5", () -> {
            webCalc.setTypeOfCalc(Programmer).enterCharacters(TWO, PLUS, THREE).submit(EQUALS);

            step("UI: assert", () -> {
                assertThat(webCalc.getCalculationResult()).isEqualTo("5");
            });
        });
    }

    @Test
    public void testFormula2() {
        step("Formula: 10 - 8 = 2", () -> {
            webCalc.setTypeOfCalc(Programmer).enterCharacters(ONE, ZERO, MINUS, EIGHT).submit(EQUALS);

            step("UI: assert", () -> {
                assertThat(webCalc.getCalculationResult()).isEqualTo("2");
            });
        });
    }
//
//    @Test
//    public void testFormula3() {
//        webCalc.setTypeOfCalc(Scientific).enterCharacters(THREE, ZERO, SIN).submit(EQUALS);
//        assertThat(webCalc.getCalculationResult()).isEqualTo("0.5");
//    }
//
//    @Test
//    public void testFormula4() {
//        webCalc
//                .setTypeOfCalc(Scientific)
//                .enterCharacters(PARAN_L, ONE, ZERO, MINUS, TWO, PARAN_R, MULT, TWO)
//                .submit(EQUALS);
//
//        assertThat(webCalc.getCalculationResult()).isNotEqualTo("20");
//    }

}
