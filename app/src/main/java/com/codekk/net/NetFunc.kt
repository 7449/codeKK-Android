package com.codekk.net

import com.codekk.ui.base.BaseModel

import io.reactivex.annotations.NonNull
import io.reactivex.functions.Function

/**
 * by y on 2017/5/16
 */

class NetFunc<T> : Function<BaseModel<T>, T> {

    override fun apply(@NonNull tBaseModel: BaseModel<T>): T {
        if (tBaseModel.code != 0) {
            throw NetException(tBaseModel.code, tBaseModel.message)
        }
        return tBaseModel.data
    }
}