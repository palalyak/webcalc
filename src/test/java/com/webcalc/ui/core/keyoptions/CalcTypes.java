package com.webcalc.ui.core.keyoptions;


import lombok.Getter;

public enum CalcTypes {
    Scientific ("Scientific"),
    Programmer("Programmer"),
    Fractions("Fractions"),
    Equations("Equations"),
    Vectors("Vectors/Matrices"),
    UnitConverter("Unit Converter");

    CalcTypes(String value) {
        this.value = value;
    }

    @Getter
    private String value;
}
