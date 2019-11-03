package com.codekk.ui.base

import io.reactivex.network.RxNetWorkListener

/**
 * by y on 2017/5/16
 */
abstract class BasePresenterImpl<V : BaseView<M>, M>(var view: V?) : RxNetWorkListener<M> {

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

    open fun onDestroy() {
        if (view != null)
            view = null
    }
}
