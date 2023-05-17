package com.webcalc.ui.core.keyoptions;


import lombok.Getter;

public enum CalcTypes {
    Scientific ("scientific"),
    Programmer("programmer");

    CalcTypes(String value) {
        this.value = value;
    }

    @Getter
    private String value;
}
