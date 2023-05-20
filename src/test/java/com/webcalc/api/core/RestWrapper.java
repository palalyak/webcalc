package com.webcalc.api.core;

public class RestWrapper {

    private CalcService calcService;

    public static RestWrapper request() {
        return new RestWrapper();
    }
    public CalcService calcService() {
        if(calcService == null) {
            calcService = new CalcService();
        }
        return calcService;
    }

}
