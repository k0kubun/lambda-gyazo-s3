package com.github.k0kubun.lambda.gyazo;

import java.util.HashMap;
import java.util.Map;

class Multipart {
    HashMap<String, ContentDisposition> formData;

    public Multipart(String rawBody, String boundary) {
        this.formData = parseFormData(rawBody, boundary);
    }

    public Map<String, ContentDisposition> getFormData() {
        return formData;
    }

    private HashMap<String, ContentDisposition> parseFormData(String rawBody, String boundary) {
        String[] rawDatas = rawBody.
            replaceFirst("--" + boundary + "--", "").split("--" + boundary, 0);

        HashMap<String, ContentDisposition> ret = new HashMap<String, ContentDisposition>();
        for (String rawData : rawDatas) {
            if (rawData.length() > 0) {
                ContentDisposition content = ContentDisposition.parse(rawData);
                ret.put(rawData, content);
            }
        }
        return ret;
    }
}
