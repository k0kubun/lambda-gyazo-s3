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

        HashMap<String, ContentDisposition> ret = new HashMap<>();
        for (String rawData : rawDatas) {
            if (rawData.length() > 0) {
                ContentDisposition content = new ContentDisposition(rawData);
                String name = content.parameters.get("name");
                if (name != null) {
                    ret.put(name, content);
                }
            }
        }
        return ret;
    }
}
