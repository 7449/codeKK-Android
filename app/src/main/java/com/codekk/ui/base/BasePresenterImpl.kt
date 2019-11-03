package com.codekk.ui.base

import io.reactivex.Observable
import io.reactivex.network.RxNetWork
import io.reactivex.network.RxNetWorkListener


/**
 * by y on 2017/5/16
 */

abstract class BasePresenterImpl<V : BaseView<M>, M>(var view: V?) : RxNetWorkListener<M> {

    private var netWorkTag: String? = null

    override fun onNetWorkStart() {
        view?.showProgress()
    }

    override fun onNetWorkError(e: Throwable) {
        view?.hideProgress()
        view?.netWorkError(e)
    }

    override fun onNetWorkComplete() {
        view?.hideProgress()
    }

    override fun onNetWorkSuccess(data: M) {
        view?.netWorkSuccess(data)
    }

    protected fun netWork(observable: Observable<M>) {
        RxNetWork.instance.cancel(netWorkTag ?: "")
        RxNetWork.instance.getApi(netWorkTag ?: "", observable, this)
    }

    fun setNetWorkTag(netWorkTag: String) {
        this.netWorkTag = netWorkTag
    }

    fun onDestroy() {
        if (view != null)
            view = null
    }
}
