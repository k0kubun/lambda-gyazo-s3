package com.github.k0kubun.lambda.gyazo;

import java.util.Base64;

public class Request {
    String body;
    String contentType;

    public String getBody() {
        return body;
    }

    public void setBody(String encodedBody) {
        String decodedBody = new String(Base64.getDecoder().decode(encodedBody));
        this.body = decodedBody;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
