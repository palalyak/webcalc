package com.webcalc.ui.tests;

import com.webcalc.ui.core.utils.BaseTest;
import com.webcalc.ui.pages.WebCalc;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static com.webcalc.ui.core.keyoptions.BtnCalc.*;
import static com.webcalc.ui.core.keyoptions.CalcTypes.Programmer;
import static com.webcalc.ui.core.keyoptions.CalcTypes.Scientific;
import static com.webcalc.ui.core.utils.Data.getJsonData;
import static org.assertj.core.api.Assertions.assertThat;

public class testCalBasicFormulasInput extends BaseTest {

    @Test(dataProvider = "getData")
    public void testEnteringKeyboardNumbers(HashMap<String, String>input) {
        webCalc.enterCharacters(input.get("formula")).submit();
        assertThat(webCalc.getCalculationResult()).isEqualTo(input.get("result"));
    }

    @Test(dataProvider = "getDataNegative")
    public void testEnteringKeyboardNumbersNegative(HashMap<String, String>input) {
        WebCalc calcPage = webCalc
                .enterCharacters(input.get("formula"))
                .submit();

        assertThat(calcPage.getCalculationResult()).isNotEqualTo(input.get("result"));
    }
    
    /*
              page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("5")).click();
      page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("+")).click();
      page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("9")).click();
      page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("=").setExact(true)).click();
      page.locator("#hist").getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("")).click();
      page.locator("#histframe").getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("×")).click();
      page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("")).click();
      page.locator("#hist").getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("")).click();
      page.locator("#histframe").getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("×")).click();
      page.locator("#hist").click();
      page.locator("#input").click();
      page.locator("#input").fill("5+5");
      page.locator("#input").press("Enter");
         */

//    @DataProvider(name = "input")
//    public Object[][] testDataViewDay() {
//        return new Object[][]{
//                {"sun", ViewStartOn.Sunday, "5"},
//                {"mon", ViewStartOn.Monday, "10"},
//                {"tue", ViewStartOn.Tuesday, "15"},
//                {"wed", ViewStartOn.Wednesday, "20"},
//                {"thu", ViewStartOn.Thursday, "30"},
//                {"fri", ViewStartOn.Friday, "60"},
//                {"sat", ViewStartOn.Saturday, "60"}
//        };
//    }

    @DataProvider
    public Object[][] getData() throws IOException {
        List<HashMap<String, String>> data = getJsonData("//src//test//java//com//webcalc//ui//core//utils//calcData.json");
        return new Object[][] { {data.get(0)}, {data.get(1)}};
    }
    @DataProvider
    public Object[][] getDataNegative() throws IOException {
        List<HashMap<String, String>> data = getJsonData("//src//test//java//com//webcalc//ui//core//utils//calcDataNegative.json");
        return new Object[][] { {data.get(0)}, {data.get(1)}};
    }
}
