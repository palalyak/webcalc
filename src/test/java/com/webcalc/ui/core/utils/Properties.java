package com.webcalc.ui.core.utils;
import org.aeonbits.owner.ConfigFactory;

public class Properties {
    public static Prop getProp() {
        return ConfigFactory.create(Prop.class);
    }
}
