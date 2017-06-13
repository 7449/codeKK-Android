package com.codekk.net;

import com.codekk.ui.base.BaseModel;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * by y on 2017/5/16
 */

public class NetFunc<T> implements Function<BaseModel<T>, T> {

    @Override
    public T apply(@NonNull BaseModel<T> tBaseModel) {
        if (tBaseModel.getCode() != 0) {
            throw new NetException(tBaseModel.getCode(), tBaseModel.getMessage());
        }
        return tBaseModel.getData();
    }
}