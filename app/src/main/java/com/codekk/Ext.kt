package com.codekk

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.google.android.material.snackbar.Snackbar

fun Activity.openSearch(@StringRes stringId: Int, dialogListener: (input: String) -> Unit) {
    MaterialDialog(this).show {
        title(res = R.string.search_dialog_title)
        input(hintRes = stringId) { _, text ->
            dialogListener(text.toString())
        }
    }
}

fun View.snackBar(@StringRes stringRes: Int) {
    Snackbar.make(this, stringRes, Snackbar.LENGTH_SHORT).show()
}

fun View.snackBar(@StringRes stringRes: Int, @ColorInt color: Int) {
    Snackbar.make(this, stringRes, Snackbar.LENGTH_SHORT)
            .setActionTextColor(color)
            .show()
}

fun Context.sharedPreferences(): SharedPreferences = applicationContext.getSharedPreferences("codeKK", Context.MODE_PRIVATE)

fun Context.opTagBoolean(): Boolean = sharedPreferences().getBoolean("op_tag", true)

fun Context.opaTagBoolean(): Boolean = sharedPreferences().getBoolean("opa_tag", true)

fun Context.opUriWebBoolean(): Boolean = sharedPreferences().getBoolean("op_uri_web", true)

fun Context.opaUriWebBoolean(): Boolean = sharedPreferences().getBoolean("opa_uri_web", true)

fun Context.blogTagBoolean(): Boolean = sharedPreferences().getBoolean("blog_tag", true)

fun Context.opTagBoolean(value: Boolean) = sharedPreferences().edit().putBoolean("op_tag", value).apply()

fun Context.opaTagBoolean(value: Boolean) = sharedPreferences().edit().putBoolean("opa_tag", value).apply()

fun Context.opUriWebBoolean(value: Boolean) = sharedPreferences().edit().putBoolean("op_uri_web", value).apply()

fun Context.opaUriWebBoolean(value: Boolean) = sharedPreferences().edit().putBoolean("opa_uri_web", value).apply()

fun Context.blogTagBoolean(value: Boolean) = sharedPreferences().edit().putBoolean("blog_tag", value).apply()


