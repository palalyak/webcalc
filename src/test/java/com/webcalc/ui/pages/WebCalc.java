package com.webcalc.ui.pages;

import com.webcalc.ui.core.keyoptions.BtnCalc;
import com.webcalc.ui.core.utils.BasePage;
import com.webcalc.ui.core.keyoptions.CalcTypes;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;

import static org.assertj.core.api.Assertions.assertThat;
import static io.qameta.allure.Allure.step;

public class WebCalc extends BasePage {

    private Page page;
    private String helperMenu = "#inputhelper";
    private String currentActiveCalcType = "#inputhelpermenu li[class=active]";
    private String popup = ".modal-content button[value='consent']";
    private String titleType = "#inputhelper .title";

    public WebCalc(Page page) {
        super(page);
        this.page = page;
    }

    @Step("Enter characters")
    public WebCalc enterCharacters(BtnCalc... btnCalc) {
        for (BtnCalc button : btnCalc) {
            clickBy(button.getValue(), 0, false);
        }
        return this;
    }

    @Step("Click on characters")
    public WebCalc enterCharacters(String formula) {
        typeIn("#input", formula);
        waitForTimeout(2000);
        return this;
    }

    @Step("Set calculator type")
    public WebCalc setTypeOfCalc(CalcTypes calcTypes) {
        step("get current active calculator type", () -> {
            String currentType = getInnerTextBy(currentActiveCalcType);

            if (!currentType.equals(calcTypes.getValue())) {
                step("open helper menu", () -> {
                    clickBy(helperMenu, 0, false);
                });
                step("change calculator type", () -> {
                    clickBy("#inputhelpermenu a[class='"+ calcTypes.getValue() +"']", 0, false);

                    assertThat(getInnerTextBy(titleType)).as("type was changed")
                            .isEqualTo(calcTypes.getValue().toLowerCase());
                });
            }
        });
        return this;
    }

    @Step("close popup")
    public void closePopup() {
        waitForTimeout(2000);
        if (isVisible(popup)) {
            clickBy(popup, 0, true);
            waitForTimeout(3000);
        }
        assertThat(!isVisible(popup)).as("popup is closed").isTrue();
    }

    public WebCalc submit(BtnCalc btnCalc) {
        clickBy(btnCalc.getValue(), 0, false);
        clickBy("#hist",0,false);
        waitForTimeout(2000);
        return this;
    }

    public WebCalc submit() {
        page.keyboard().press("Enter");
        waitForTimeout(2000);
        return this;
    }

    @Step("pull result from input line")
    public String getCalculationResult() {
        String result = extractJSValue("#input");
        getLocator("#input").clear();
        return result;
    }


}
