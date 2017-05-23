package com.common.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * by y on 2017/5/18
 */

public class SPUtils {
    private SPUtils() {
    }

    private static final String SHAREDPREFERENCES_NAME = "codeKK";
    private static SharedPreferences sharedPreferences;

    public static final String IS_OP_TAG = "op_tag";
    public static final String IS_OP_URL_WEB = "op_tvWeb";
    public static final String IS_OPA_TAG = "opa_tag";
    public static final String IS_OPA_URL_WEB = "opa_web";
    public static final String IS_BLOG_TAG = "blog_tag";

    private static void initSharePreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static void init(Context context) {
        initSharePreferences(context.getApplicationContext());
    }

    public static boolean isNull() {
        return sharedPreferences == null;
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        if (isNull()) {
            return defaultValue;
        }
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public static void setBoolean(String key, boolean value) {
        if (isNull()) {
            return;
        }
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    public static String getString(String key, String defaultValue) {
        if (isNull()) {
            return defaultValue;
        }
        return sharedPreferences.getString(key, defaultValue);
    }

    public static void setString(String key, String value) {
        if (isNull()) {
            return;
        }
        sharedPreferences.edit().putString(key, value).apply();
    }

    public static void clearAll() {
        if (isNull()) {
            return;
        }
        sharedPreferences.edit().clear().apply();
    }

}
