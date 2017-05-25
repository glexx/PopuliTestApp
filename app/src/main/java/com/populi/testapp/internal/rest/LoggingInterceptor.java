package com.populi.testapp.internal.rest;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * Created by Alexander Gavrikov.
 */
public class LoggingInterceptor implements Interceptor {

    // TODO: LoggingInterceptor doesn't show finish url for template requests (offer for example). Improve it.

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        long t1 = System.nanoTime();

        Buffer buffer = new Buffer();
        String requestBody = "";
        if (request.body() != null){
            request.body().writeTo(buffer);
            requestBody = "\n" + buffer.readUtf8() + "\n---";

            if (requestBody.length() > 1024){
                requestBody = requestBody.substring(0, 1024);
            }
        }
        Log.d("SDK rest API", String.format("=== Request (%s) %s %s \n%s\n", request.method(), request.url(), requestBody, request.headers()));

        Response response = chain.proceed(request);

        long t2 = System.nanoTime();

        String responseString = response.body().string();
        Log.w("SDK rest API", String.format("=== Response for %s in %.1fms with CODE = %d\n%s\n---\n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.code(), responseString, response.headers()));

        ResponseBody newBody = ResponseBody.create(response.body().contentType(), responseString);
        return response.newBuilder()
                .body(newBody)
                .build();
    }
}
