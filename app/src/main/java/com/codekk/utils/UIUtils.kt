package com.codekk.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import com.codekk.App
import com.google.android.material.snackbar.Snackbar

/**
 * by y on 2017/5/16
 */

object UIUtils {

    val context: Context
        get() = App.instance

    fun getString(@StringRes id: Int): String {
        return context.resources.getString(id)
    }

    fun startActivity(clz: Class<*>) {
        val intent = Intent(context, clz)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    fun startActivity(clz: Class<*>, bundle: Bundle) {
        val intent = Intent(context, clz)
        intent.putExtras(bundle)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    fun toast(`object`: Any) {
        Toast.makeText(context, `object`.toString() + "", Toast.LENGTH_LONG).show()
    }

    fun snackBar(view: View, @StringRes stringRes: Int) {
        Snackbar.make(view, stringRes, Snackbar.LENGTH_SHORT).show()
    }

    fun snackBar(view: View, @StringRes stringRes: Int, @ColorInt color: Int) {
        Snackbar.make(view, stringRes, Snackbar.LENGTH_SHORT)
                .setActionTextColor(color)
                .show()
    }

    fun openBrowser(url: String) {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.data = Uri.parse(url)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }
}
