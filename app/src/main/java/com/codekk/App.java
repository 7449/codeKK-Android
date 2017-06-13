package com.codekk;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.codekk.net.Api;
import com.codekk.net.SimpleLogInterceptor;
import com.common.util.SPUtils;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import io.reactivex.network.manager.RxNetWork;


/**
 * by y on 2017/5/16
 */

public class App extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context context;
    private RefWatcher install;

    public static App getInstance() {
        return (App) context;
    }

    public static RefWatcher get(Context context) {
        App app = (App) context.getApplicationContext();
        return app.install;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        install = LeakCanary.install(this);
        SPUtils.init(getApplicationContext());

        RxNetWork
                .getInstance()
                .setBaseUrl(Api.BASE_API)
                .setLogInterceptor(new SimpleLogInterceptor());
    }

}
