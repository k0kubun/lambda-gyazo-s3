package com.github.k0kubun.lambdaGyazoS3.routes.api.v1.gyazo;

import com.amazonaws.services.lambda.runtime.Context;

public class Gyazo {
    public String get(final Context context) {
        return "Hello world!";
    }
}
