package com.codekk.utils

import android.app.Activity
import androidx.annotation.StringRes
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.codekk.R

/**
 * by y on 2017/5/17
 */

object MaterialDialogUtils {

    fun openSearch(activity: Activity, @StringRes stringId: Int, dialogListener: (input: String) -> Unit) {
        MaterialDialog(activity).show {
            title(res = R.string.search_dialog_title)
            input(hintRes = stringId) { _, text ->
                dialogListener(text.toString())
            }
        }
    }
}
