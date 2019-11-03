package com.codekk.ext

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.android.status.layout.StatusLayout
import com.codekk.R
import com.codekk.ui.widget.FlowText
import com.google.android.flexbox.FlexboxLayout
import com.google.android.material.snackbar.Snackbar

fun Activity.openSearch(@StringRes stringId: Int, dialogListener: (input: String) -> Unit) {
    MaterialDialog(this).show {
        title(res = R.string.search_dialog_title)
        input(hintRes = stringId) { _, text ->
            dialogListener(text.toString())
        }
    }
}

fun OpListBean.tags(): ArrayList<String>? {
    val arrayList = ArrayList<String>()
    tags?.let {
        for (opTagsBean in it) {
            arrayList.add(opTagsBean.name)
        }
        return arrayList
    }
    return null
}

fun FlexboxLayout.tags(tags: List<String>?, click: (tag: String) -> Unit) {
    removeAllViews()
    tags?.let {
        for (element in tags) {
            val flowText = FlowText(context)
            flowText.text = element
            addView(flowText)
            flowText.setOnClickListener { click.invoke(element) }
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

fun StatusLayout.error() = run { status = StatusLayout.ERROR }

fun StatusLayout.empty() = run { status = StatusLayout.EMPTY }

fun StatusLayout.success() = run { status = StatusLayout.SUCCESS }

fun Context.sharedPreferences(): SharedPreferences = applicationContext.getSharedPreferences("codeKK", Context.MODE_PRIVATE)

fun Context.opTagBoolean(): Boolean = sharedPreferences().getBoolean("op_tag", true)
fun Context.opTagBoolean(value: Boolean) = sharedPreferences().edit().putBoolean("op_tag", value).apply()

fun Context.opUriWebBoolean(): Boolean = sharedPreferences().getBoolean("op_uri_web", true)
fun Context.opUriWebBoolean(value: Boolean) = sharedPreferences().edit().putBoolean("op_uri_web", value).commit()

fun Context.opaTagBoolean(): Boolean = sharedPreferences().getBoolean("opa_tag", true)
fun Context.opaTagBoolean(value: Boolean) = sharedPreferences().edit().putBoolean("opa_tag", value).apply()

fun Context.opaUriWebBoolean(): Boolean = sharedPreferences().getBoolean("opa_uri_web", true)
fun Context.opaUriWebBoolean(value: Boolean) = sharedPreferences().edit().putBoolean("opa_uri_web", value).apply()

fun Context.blogTagBoolean(): Boolean = sharedPreferences().getBoolean("blog_tag", true)
fun Context.blogTagBoolean(value: Boolean) = sharedPreferences().edit().putBoolean("blog_tag", value).apply()