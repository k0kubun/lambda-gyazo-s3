package com.github.k0kubun.lambda.gyazo;

import com.amazonaws.services.lambda.runtime.Context;

public class GyazoHandler {
    public String post(final Request request, final Context context) {
        ContentType contentType = new ContentType(request.getContentType());
        return "Boundary: " + contentType.getParameters().get("boundary") +
            "\nBase64 body: " + request.getBody();
    }
}
