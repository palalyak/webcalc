package com.webcalc.performance;

import io.restassured.response.ValidatableResponse;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.webcalc.ui.core.utils.Properties.getProp;
import static io.restassured.RestAssured.given;

public class RestAssuredThread extends Thread {

    private int threadNumber;
    public static Map<Integer, Long> times;
    public static int failures;

    static {
        times = Collections.synchronizedMap(new HashMap<>());
    }

    public RestAssuredThread(int numb) {
        threadNumber = numb;
    }

    public void run() {
        System.out.println("start thread " + threadNumber);
        String trigValue = "deg";
        String pValue = "0";
        String sValue = "0";

        String requestBody = null;
        try {
            requestBody = "in%5B%5D=" + URLEncoder.encode("1+1", "UTF-8")
                    + "&trig=" + URLEncoder.encode(trigValue, "UTF-8")
                    + "&p=" + URLEncoder.encode(pValue, "UTF-8")
                    + "&s=" + URLEncoder.encode(sValue, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        ValidatableResponse response = given().baseUri(getProp().baseURL()).basePath("calc").log().all()
                .body(requestBody).post().then();

        if(response.extract().statusCode() != 200)
            failures++;
        times.put(threadNumber, response.extract().time());
    }
}
