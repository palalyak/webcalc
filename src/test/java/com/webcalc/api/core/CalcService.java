package com.webcalc.api.core;

import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static io.restassured.RestAssured.given;

public class CalcService extends RestService{
    @Override
    protected String getBasePath() {
        return "calc";
    }

    @Step("api: post")
    public String calc(String keys) throws UnsupportedEncodingException {
        String trigValue = "deg";
        String pValue = "0";
        String sValue = "0";

            String requestBody = "in%5B%5D=" + URLEncoder.encode(keys, "UTF-8")
                    + "&trig=" + URLEncoder.encode(trigValue, "UTF-8")
                    + "&p=" + URLEncoder.encode(pValue, "UTF-8")
                    + "&s=" + URLEncoder.encode(sValue, "UTF-8");

        JsonPath jsonPath = given().spec(getREQ_SPEC_ENCODED()).log().all()
                .body(requestBody).post().then().assertThat()
                .statusCode(200).extract().body().jsonPath();

        return jsonPath.getString("results[0].out");
    }
}
