package com.codekk.utils;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.afollestad.materialdialogs.MaterialDialog;
import com.codekk.R;

/**
 * by y on 2017/5/17
 */

public class MaterialDialogUtils {
    private MaterialDialogUtils() {
    }


    public static void openSearch(@StringRes int stringId, @NonNull SearchDialogListener dialogListener) {
        new MaterialDialog
                .Builder(dialogListener.getSearchActivity())
                .title(UIUtils.getString(R.string.search_dialog_title))
                .inputRange(1, -1)
                .input(
                        UIUtils.getString(stringId),
                        null,
                        (dialog, input) -> dialogListener.onSearchNext(input.toString()))
                .show();
    }

    public interface SearchDialogListener {
        Activity getSearchActivity();

        void onSearchNext(@NonNull String text);
    }
}
