package com.codekk.net

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException

/**
 * by y on 2017/5/16
 */

class SimpleLogInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        response.body()?.let {
            val mediaType = it.contentType()
            val content = it.string()
            Log.i(TAG, HEADER_TITLE + response.toString())
            Log.i(TAG, CONTENT_TITLE + content)
            return response.newBuilder().body(ResponseBody.create(mediaType, content)).build()
        }
        return response
    }

    companion object {
        private const val TAG = "RxNetWorkApiLog"
        private const val CONTENT_TITLE = "Api Content-->:"
        private const val HEADER_TITLE = "Api Header-->"
    }
}
