package com.github.k0kubun.lambda.gyazo;

import java.util.HashMap;
import java.util.Map;

class ContentDisposition {
    String body;
    Map<String, String> parameters;

    public static ContentDisposition parse(String rawData) {
        // TODO: Parse parameters
        Map<String, String> parameters = new HashMap<String, String>();
        return new ContentDisposition("hello", parameters);
    }

    public ContentDisposition(String body, Map<String, String> parameters) {
        this.body = body;
        this.parameters = parameters;
    }

    public String getBody() {
        return body;
    }
}
