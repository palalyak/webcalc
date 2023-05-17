package com.webcalc.ui.core.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

public class Data {

    public static List<HashMap<String, String>> getJsonData(String jsonPath) throws IOException {
        //"//src//main//java//Utils//eCommerceData.json"
        String jsonContent  =
                FileUtils.readFileToString(new File
                        (System.getProperty("user.dir") + jsonPath), StandardCharsets.UTF_8);

        ObjectMapper mapper = new ObjectMapper();
        List<HashMap<String, String>> data = mapper.readValue(jsonContent,
                new TypeReference<List<HashMap<String, String>>>() {
                });
        return data;
    }
}
