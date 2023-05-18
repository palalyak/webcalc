package com.webcalc.ui.pages;

import com.microsoft.playwright.Locator;
import com.webcalc.ui.core.keyoptions.BtnCalc;
import com.webcalc.ui.core.utils.BasePage;
import com.webcalc.ui.core.keyoptions.CalcTypes;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static io.qameta.allure.Allure.step;

public class WebCalc extends BasePage {

    private Page page;
    private String helperMenu = "#inputhelper";
    private String openHistoryWindow = "#hist.open";
    private String historyLinesEl = "#histframe > ul > li";
    private String titleType = "#inputhelper .title";
    private String popup = ".modal-content button[value='consent']";
    private String currentActiveCalcType = "#inputhelpermenu li[class=active]";

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
                    clickBy("#inputhelpermenu a[class='" + calcTypes.getValue() + "']", 0, false);

                    assertThat(getInnerTextBy(titleType)).as("type was changed")
                            .isEqualTo(calcTypes.getValue().toLowerCase());
                });
            }
        });
        return this;
    }

    @Step("close popup")
    public WebCalc closePopup() {
        waitForTimeout(2000);
        if (isVisible(popup)) {
            clickBy(popup, 0, true);
            waitForTimeout(3000);
        }
        assertThat(!isVisible(popup)).as("popup is closed").isTrue();
        return this;
    }

    @Step("submit result by click on =")
    public WebCalc submit(BtnCalc btnCalc) {
        clickBy(btnCalc.getValue(), 0, false);
        getHistory();
        waitForTimeout(2000);
        return this;
    }

    @Step("submit result by press key - Enter")
    public WebCalc submit() {
        page.keyboard().press("Enter");
        waitForTimeout(2000);
        return this;
    }

    @Step("extract formula and result from history window")
    public Map<String, String> getHistory() {
        return getLocator(historyLinesEl).all().stream()
                .collect(Collectors.toMap(
                        liElement -> liElement.locator("p.l").innerText(),
                        liElement -> liElement.locator("p.r").innerText()
                                .replaceAll("[\\s=\\u00A0]", "")
                ));
    }

    @Step("pull result from input line")
    public String getCalculationResult() {
        return extractJSValue("#input");
    }


}
