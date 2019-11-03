package com.codekk.ui.base

/**
 * by y on 2017/5/16
 */
interface BaseView<T> {

    fun showProgress()

    fun hideProgress()

    fun netWorkSuccess(entity: T)

    fun netWorkError(throwable: Throwable)

}
