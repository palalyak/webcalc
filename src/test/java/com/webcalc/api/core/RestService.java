package com.webcalc.api.core;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static com.webcalc.ui.core.utils.Properties.getProp;
import static io.restassured.config.EncoderConfig.encoderConfig;

public abstract class RestService {
    public final String BASE_URL = getProp().baseURL();
    private RequestSpecification REQ_SPEC_ENCODED;
    protected abstract String getBasePath();

    public RequestSpecification getREQ_SPEC_ENCODED() {
        if (REQ_SPEC_ENCODED == null) {
            REQ_SPEC_ENCODED = get()
                    .setBasePath(getBasePath())
                    .setContentType(ContentType.URLENC).setConfig(RestAssured.config().encoderConfig(encoderConfig()
                            .appendDefaultContentCharsetToContentTypeIfUndefined(false)))
                    .build();
        }
        return REQ_SPEC_ENCODED;
    }

    private RequestSpecBuilder get() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL);
    }

}
