package com.webcalc.ui.core.keyoptions;

import lombok.Getter;

public enum BtnCalc {
    ZERO ("#Btn0"),
    ONE ("#Btn1"),
    TWO ("#Btn2"),
    THREE ("#Btn3"),
    FOUR ("#Btn4"),
    FIVE ("#Btn5"),
    SIX ("#Btn6"),
    SEVEN ("#Btn7"),
    EIGHT ("#Btn8"),
    NINE ("#Btn9"),
    PLUS ("#BtnPlus"),

    EQUALS ("#BtnCalc"),
    MINUS ("#BtnMinus"),
    MULT ("#BtnMult"),
    PARAN_L ("#BtnParanL"),
    PARAN_R ("#BtnParanR"),
    SIN ("#BtnSin");


    BtnCalc(String value) {
        this.value = value;
    }

    @Getter
    private String value;
}
