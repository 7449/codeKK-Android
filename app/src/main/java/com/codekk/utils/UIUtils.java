package com.codekk.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import com.codekk.App;

/**
 * by y on 2017/5/16
 */

public class UIUtils {
    private UIUtils() {
    }

    public static Context getContext() {
        return App.getInstance();
    }


    public static int getColor(@ColorRes int id) {
        return ContextCompat.getColor(getContext(), id);
    }


    public static String getString(@StringRes int id) {
        return getContext().getResources().getString(id);
    }

    public static View getInflate(@LayoutRes int layout) {
        return View.inflate(getContext(), layout, null);
    }

    public static void startActivity(@NonNull Class<?> clz) {
        Intent intent = new Intent(getContext(), clz);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getContext().startActivity(intent);
    }

    public static void startActivity(@NonNull Class<?> clz, @NonNull Bundle bundle) {
        Intent intent = new Intent(getContext(), clz);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getContext().startActivity(intent);
    }

    public static void toast(Object object) {
        Toast.makeText(getContext(), object + "", Toast.LENGTH_LONG).show();
    }

    public static void snackBar(@NonNull View view, @StringRes int stringRes) {
        Snackbar.make(view, stringRes, Snackbar.LENGTH_SHORT).show();
    }

    public static void snackBar(@NonNull View view, @StringRes int stringRes, @ColorInt int color) {
        Snackbar.make(view, stringRes, Snackbar.LENGTH_SHORT)
                .setActionTextColor(color)
                .show();
    }

    public static void openBrowser(@NonNull String url) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getContext().startActivity(intent);
    }
}
