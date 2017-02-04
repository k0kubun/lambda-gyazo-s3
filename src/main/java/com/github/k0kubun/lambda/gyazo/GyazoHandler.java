package com.github.k0kubun.lambda.gyazo;

import com.amazonaws.services.lambda.runtime.Context;

public class GyazoHandler {
    public String post(final Request request, final Context context) {
        return "Content Type: " + request.getContentType() +
            "\nBase64 body: " + request.getBody();
    }
}
