package com.codekk.net

import com.codekk.utils.UIUtils

/**
 * by y on 2017/5/16
 */

class NetException(code: Int, message: String) : RuntimeException() {

    init {
        UIUtils.toast(code.toString() + "---" + message)
    }

}