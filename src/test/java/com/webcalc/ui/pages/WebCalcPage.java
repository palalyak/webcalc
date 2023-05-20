package com.webcalc.ui.pages;

import com.microsoft.playwright.Page;
import com.webcalc.ui.core.keyoptions.BtnCalc;
import com.webcalc.ui.core.keyoptions.CalcTypes;
import com.webcalc.ui.core.utils.BasePage;
import io.qameta.allure.Step;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;


public class WebCalcPage extends BasePage {

    private Page page;
    private String helperMenu = "#inputhelper";
    private String historyLinesEl = "#histframe > ul > li";
    private String popup = ".modal-content button[value='consent']";
    private String currentActiveCalcType = "#inputhelpermenu li[class=active]";


    public WebCalcPage(Page page) {
        super(page);
        this.page = page;
    }

    @Step("Enter characters")
    public WebCalcPage enterCharacters(BtnCalc... btnCalc) {
        for (BtnCalc button : btnCalc) {
            clickBy(button.getValue(), 0, false);
        }
        return this;
    }

    @Step("Click on characters")
    public WebCalcPage enterCharacters(String formula) {
        typeIn("#input", formula);
        waitForTimeout(2000);
        return this;
    }

    @Step("Set calculator type")
    public WebCalcPage setTypeOfCalc(CalcTypes calcTypes) {
        step("get current active calculator type", () -> {
            String currentType = getInnerTextBy(currentActiveCalcType);

            if (!currentType.equals(calcTypes.getValue())) {
                step("open helper menu", () -> {
                    clickBy(helperMenu, 0, false);
                });
                step("change calculator type", () -> {
                    clickBy(hasText("#inputhelpermenu a", calcTypes.getValue()), 0, false);
                });
            }
        });
        return this;
    }

    @Step("close popup")
    public WebCalcPage closePopup() {
        waitForTimeout(2000);
        if (isVisible(popup)) {
            clickBy(popup, 0, true);
            waitForTimeout(3000);
        }
        assertThat(!isVisible(popup)).as("popup is closed").isTrue();
        return this;
    }

    @Step("submit result by click on =")
    public WebCalcPage submit(BtnCalc btnCalc) {
        clickBy(btnCalc.getValue(), 0, false);
        getHistoryOfResults();
        waitForTimeout(2000);
        return this;
    }

    @Step("submit result by press key - Enter")
    public WebCalcPage submit() {
        page.keyboard().press("Enter");
        waitForTimeout(2000);
        return this;
    }

    @Step("collect formulas and result from history window")
    public Map<String, String> getHistoryOfResults() {
        clickBy("#hist",0,false);
        return getLocator(historyLinesEl).all().stream()
                .collect(Collectors.toMap(
                        liElement -> liElement.locator("p.l").innerText(),
                        liElement -> liElement.locator("p.r").innerText()
                                .replaceAll("[\\s=\\u00A0]", ""),
                        (value1, value2) -> value2
                ));
    }

    @Step("collect formulas from history window")
    public List<String> getHistoryEntries() {
        List<String> keys = getLocator(historyLinesEl).all().stream()
                .map(liElement -> liElement.locator("p.l").innerText())
                .collect(Collectors.toList());

        return keys.stream()
                .filter(key -> keys.indexOf(key) != keys.lastIndexOf(key))
                .collect(Collectors.toList());
    }
    @Step("pull result from input line")
    public String getCalculationResult() {
        return extractJSValue("#input");
    }


}
