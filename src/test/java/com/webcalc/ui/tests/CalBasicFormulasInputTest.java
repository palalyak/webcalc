package com.webcalc.ui.tests;

import com.webcalc.ui.core.utils.BaseTest;
import org.testng.annotations.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class CalBasicFormulasInputTest extends BaseTest {

//    @Test(dataProvider = "getData")
//    public void testEnteringKeyboardNumbers(HashMap<String, String> input) {
//        webCalc.enterCharacters(input.get("formula")).submit();
//        assertThat(webCalc.getCalculationResult()).isEqualTo(input.get("result"));
//    }
//
//    @Test(dataProvider = "getDataNegative")
//    public void testEnteringKeyboardNumbersNegative(HashMap<String, String>input) {
//        WebCalc calcPage = webCalc
//                .enterCharacters(input.get("formula"))
//                .submit();
//
//        assertThat(calcPage.getCalculationResult()).isNotEqualTo(input.get("result"));
//    }
//
//    @DataProvider
//    public Object[][] getData() throws IOException {
//        List<HashMap<String, String>> data = getJsonData("//src//test//java//com//webcalc//ui//core//utils//calcData.json");
//        return new Object[][] { {data.get(0)}, {data.get(1)}};
//    }
//    @DataProvider
//    public Object[][] getDataNegative() throws IOException {
//        List<HashMap<String, String>> data = getJsonData("//src//test//java//com//webcalc//ui//core//utils//calcDataNegative.json");
//        return new Object[][] { {data.get(0)}, {data.get(1)}};
//    }
}
