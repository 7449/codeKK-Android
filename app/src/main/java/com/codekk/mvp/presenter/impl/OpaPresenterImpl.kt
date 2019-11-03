package com.codekk.mvp.presenter.impl

import com.codekk.ext.NetFunc
import com.codekk.ext.OpaListModel
import com.codekk.ext.OpaService
import com.codekk.mvp.presenter.OpaPresenter
import com.codekk.mvp.view.OpaListView
import com.codekk.ui.base.BasePresenterImpl
import io.reactivex.network.RxNetWork
import io.reactivex.network.cancelTag
import io.reactivex.network.getApi

/**
 * by y on 2017/5/18
 */
class OpaPresenterImpl(view: OpaListView) : BasePresenterImpl<OpaListView, OpaListModel>(view), OpaPresenter {

    override fun netWorkRequestList(page: Int) {
        RxNetWork
                .observable(OpaService::class.java)
                .getOpaList(page)
                .cancelTag(javaClass.simpleName)
                .map(NetFunc())
                .getApi(javaClass.simpleName, this)
    }

    override fun netWorkRequestSearch(text: String, page: Int) {
        RxNetWork
                .observable(OpaService::class.java)
                .getOpaSearch(text, page)
                .cancelTag(javaClass.simpleName)
                .map(NetFunc())
                .getApi(javaClass.simpleName, this)
    }

    override fun onDestroy() {
        super.onDestroy()
        RxNetWork.cancelTag(javaClass.simpleName)
    }

    override fun onNetWorkSuccess(data: OpaListModel) {
        if (data.opaList.isEmpty()) {
            view?.noMore()
        } else {
            super.onNetWorkSuccess(data)
        }
    }
}
