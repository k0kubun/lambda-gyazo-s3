package com.github.k0kubun.lambda.gyazo;

import com.amazonaws.services.lambda.runtime.Context;

public class GyazoHandler {
    public String post(final Context context) {
        return "Hello world!";
    }
}
