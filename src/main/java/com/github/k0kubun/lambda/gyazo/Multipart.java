package com.github.k0kubun.lambda.gyazo;

import java.util.HashMap;
import java.util.Map;

class Multipart {
    HashMap<String, ContentDisposition> contents;

    public Multipart(String rawBody, String boundary) {
        this.contents = parseContents(rawBody, boundary);
    }

    public Map<String, ContentDisposition> getContents() {
        return contents;
    }

    private HashMap<String, ContentDisposition> parseContents(String rawBody, String boundary) {
        String[] rawContents = rawBody.
            replaceFirst("--" + boundary + "--", "").split("--" + boundary, 0);

        HashMap<String, ContentDisposition> ret = new HashMap<>();
        for (String rawContent : rawContents) {
            if (rawContent.length() > 0) {
                ContentDisposition content = new ContentDisposition(rawContent);
                String name = content.parameters.get("name");
                if (name != null) {
                    ret.put(name, content);
                }
            }
        }
        return ret;
    }
}
