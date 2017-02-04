package com.github.k0kubun.lambda.gyazo;

import java.util.Base64;

public class Request {
    String body;

    public String getBody() {
        return body;
    }

    public void setBody(String encodedBody) {
        String decodedBody = new String(Base64.getDecoder().decode(encodedBody));
        this.body = decodedBody;
    }
}
