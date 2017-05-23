package com.codekk.net;

import com.codekk.utils.UIUtils;

/**
 * by y on 2017/5/16
 */

class NetException extends RuntimeException {

    NetException(int code, String message) {
        UIUtils.toast(code + "---" + message);
    }

}