package com.codekk.net;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * by y on 2017/5/16
 */

public class SimpleLogInterceptor implements Interceptor {
    private static final String TAG = "RxNetWorkApiLog";
    private static final String CONTENT_TITLE = "Api Content-->:";
    private static final String HEADER_TITLE = "Api Header-->";

    @Override
    public Response intercept(Chain chain) throws IOException {
        okhttp3.Response response = chain.proceed(chain.request());
        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        Log.i(TAG, HEADER_TITLE + response.toString());
        Log.i(TAG, CONTENT_TITLE + content);
        if (response.body() != null) {
            return response.newBuilder().body(ResponseBody.create(mediaType, content)).build();
        } else {
            return response;
        }
    }
}
