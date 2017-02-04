package com.github.k0kubun.lambda.gyazo;

import com.amazonaws.services.lambda.runtime.Context;

public class GyazoHandler {
    public String post(final Request request, final Context context) {
        String boundary = getBoundary(request);
        if (boundary == null) {
            return "bad request";
        }

        return "Boundary:\n" +
            boundary +
            "\nBase64 body:\n" + request.getBody();
    }

    private String getBoundary(final Request request) {
        if (request.getContentType() == null) {
            return null;
        }
        ContentType contentType = new ContentType(request.getContentType());
        return contentType.getParameters().get("boundary");
    }
}
