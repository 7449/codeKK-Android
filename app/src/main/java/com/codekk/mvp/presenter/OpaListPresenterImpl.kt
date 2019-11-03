package com.codekk.mvp.presenter

import com.codekk.NetFunc
import com.codekk.OpaService
import com.codekk.mvp.model.OpaListModel
import com.codekk.mvp.view.ViewManager
import com.codekk.ui.base.BasePresenterImpl

import io.reactivex.network.RxNetWork


/**
 * by y on 2017/5/18
 */

class OpaListPresenterImpl(view: ViewManager.OpaListView) : BasePresenterImpl<ViewManager.OpaListView, OpaListModel>(view), PresenterManager.OpaListPresenter {

    override fun netWorkRequest(page: Int) {
        netWork(RxNetWork
                .observable(OpaService::class.java)
                .getOpaList(page)
                .map(NetFunc()))
    }

    override fun onNetWorkSuccess(data: OpaListModel) {
        if (data.summaryArray.isEmpty()) {
            view?.noMore()
        } else {
            super.onNetWorkSuccess(data)
        }
    }
}
