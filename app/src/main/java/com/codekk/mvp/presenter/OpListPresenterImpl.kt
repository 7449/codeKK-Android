package com.codekk.mvp.presenter

import com.codekk.NetFunc
import com.codekk.OpService
import com.codekk.mvp.model.OpListModel
import com.codekk.mvp.view.ViewManager
import com.codekk.ui.base.BasePresenterImpl
import io.reactivex.network.RxNetWork


/**
 * by y on 2017/5/16
 */
class OpListPresenterImpl(view: ViewManager.OpListView) : BasePresenterImpl<ViewManager.OpListView, OpListModel>(view), PresenterManager.OpListPresenter {

    override fun netWorkRequest(page: Int) {
        netWork(RxNetWork
                .observable(OpService::class.java)
                .getOpList(page)
                .map(NetFunc()))
    }

    override fun onNetWorkSuccess(data: OpListModel) {
        if (data.projectArray.isEmpty()) {
            view?.noMore()
        } else {
            super.onNetWorkSuccess(data)
        }
    }
}
