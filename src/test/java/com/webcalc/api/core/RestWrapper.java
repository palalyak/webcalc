package com.webcalc.api.core;

import lombok.Getter;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


public class RestWrapper {

    private CalcService calcService;
    @Getter
    private String cookie;

//    @Getter
//    private AuthPojo authPojo;
//
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
