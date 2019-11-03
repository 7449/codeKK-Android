package com.codekk

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.codekk.ext.Api
import io.reactivex.network.RxNetWork
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory

/**
 * by y on 2017/5/16
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        RxNetWork.initOption {
            superBaseUrl { Api.BASE_API }
            superConverterFactory { GsonConverterFactory.create() }
            superInterceptor {
                val logInterceptor = HttpLoggingInterceptor()
                logInterceptor.level = HttpLoggingInterceptor.Level.BODY
                val arrayList = ArrayList<Interceptor>()
                arrayList.add(logInterceptor)
                arrayList
            }
        }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var context: Context

        val instance: App
            get() = context as App
    }

}
