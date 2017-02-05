package com.github.k0kubun.lambda.gyazo;

import java.util.Arrays;
import java.util.HashMap;

class ContentType {
    String type;
    String subtype;
    HashMap<String, String> parameters;

    public ContentType(String contentType) {
        String[] types  = parseTypes(contentType);
        this.type       = types[0];
        this.subtype    = types[1].replaceAll("\\s+", "");
        this.parameters = parseParameters(contentType);
    }

    public HashMap<String, String> getParameters() {
        return parameters;
    }

    private String[] parseTypes(String contentType) {
        String[] delimited = contentType.split("; ", 2);
        return delimited[0].split("/", 2);
    }

    private HashMap<String, String> parseParameters(String contentType) {
        String[] delimited = contentType.split("; ", 2);

        HashMap<String, String> ret = new HashMap<>();
        if (delimited.length == 2) {
            String[] keyAndValue = delimited[1].split("=", 2);
            ret.put(keyAndValue[0], keyAndValue[1]);
        }
        return ret;
    }
}
