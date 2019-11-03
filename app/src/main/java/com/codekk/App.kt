package com.codekk

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.codekk.net.Api
import com.codekk.utils.SPUtils
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import io.reactivex.network.RxNetWork
import io.reactivex.network.SimpleRxNetOptionFactory
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
        SPUtils.init(applicationContext)
        RxNetWork.initialization(object : SimpleRxNetOptionFactory(Api.BASE_API, GsonConverterFactory.create()) {})
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
