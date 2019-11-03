package com.codekk

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import io.reactivex.network.RxNetWork
import retrofit2.converter.gson.GsonConverterFactory

/**
 * by y on 2017/5/16
 */
class App : Application() {

    private lateinit var install: RefWatcher

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        install = LeakCanary.install(this)
        RxNetWork.initOption {
            superBaseUrl { Api.BASE_API }
            superConverterFactory { GsonConverterFactory.create() }
        }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var context: Context

        val instance: App
            get() = context as App

        operator fun get(context: Context): RefWatcher {
            val app = context.applicationContext as App
            return app.install
        }
    }

}
