package com.github.k0kubun.lambda.gyazo;

import com.amazonaws.services.lambda.runtime.Context;
import java.util.Map;

public class GyazoHandler {
    public String post(final Request request, final Context context) {
        String boundary = getBoundary(request);
        if (boundary == null) {
            return "bad request";
        }

        Multipart multipart = new Multipart(request.getBody(), boundary);
        return buildResponse(multipart);
    }

    private String getBoundary(final Request request) {
        if (request.getContentType() == null) {
            return null;
        }
        ContentType contentType = new ContentType(request.getContentType());
        return contentType.getParameters().get("boundary");
    }

    private String buildResponse(Multipart multipart) {
        String ret = "";
        for (Map.Entry<String, ContentDisposition> data : multipart.getFormData().entrySet()) {
            ret += "\nKey:";
            ret += data.getKey();
            ret += "\nValue:";
            ret += data.getValue().getBody().length();
            ret += "\n";
        }
        return ret;
    }
}
