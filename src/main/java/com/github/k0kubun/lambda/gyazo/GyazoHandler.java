package com.github.k0kubun.lambda.gyazo;

import com.amazonaws.services.lambda.runtime.Context;
import java.util.Map;

public class GyazoHandler {
    public String post(final Request request, final Context context) throws BadRequestException {
        String boundary = getBoundary(request);
        if (boundary == null) {
            throw new BadRequestException("No boundary given!");
        }

        Multipart multipart = new Multipart(request.getBody(), boundary);
        String imageData = getImageData(multipart);
        if (imageData == null) {
            throw new BadRequestException("No imagedata given!");
        }

        ImageStorage storage = new ImageStorage();
        return storage.upload(imageData);
    }

    private String getBoundary(final Request request) {
        if (request.getContentType() == null) {
            return null;
        }
        ContentType contentType = new ContentType(request.getContentType());
        return contentType.getParameters().get("boundary");
    }

    private String getImageData(final Multipart multipart) {
        if (!multipart.contents.containsKey("imagedata")) {
            return null;
        }
        ContentDisposition imageContent = multipart.contents.get("imagedata");
        return imageContent.getBody();
    }
}
