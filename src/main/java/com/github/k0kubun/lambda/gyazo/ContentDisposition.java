package com.github.k0kubun.lambda.gyazo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class ContentDisposition {
    String type;
    String body;
    Map<String, String> parameters;

    public ContentDisposition(String rawData) {
        String[] fields = rawData.split("\n", 3);
        this.type = parseType(fields[1]);
        this.parameters = parseParameters(fields[1]);
        this.body = fields[2];
    }

    public String getBody() {
        return body;
    }

    private Map<String, String> parseParameters(String dataHeader) {
        String[] delimited = dataHeader.substring(0, dataHeader.length()-1).split("; ", 0);
        String[] rawParameters = Arrays.copyOfRange(delimited, 1, delimited.length);

        Map<String, String> parameters = new HashMap<>();
        for (String rawParameter: rawParameters) {
            String[] keyAndValue = rawParameter.split("=", 2);
            String value = keyAndValue[1];
            value = value.substring(1, value.length()-1);
            parameters.put(keyAndValue[0], value);
        }
        return parameters;
    }

    private String parseType(String dataHeader) {
        String firstFragment = dataHeader.split("; ", 2)[0];
        return firstFragment.split(": ", 2)[1];
    }
}
