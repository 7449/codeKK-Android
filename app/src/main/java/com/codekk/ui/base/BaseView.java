package com.codekk.ui.base;

/**
 * by y on 2017/5/16
 */

public interface BaseView<T> {

    void showProgress();

    void hideProgress();

    void netWorkSuccess(T t);

    void netWorkError(Throwable e);

}
