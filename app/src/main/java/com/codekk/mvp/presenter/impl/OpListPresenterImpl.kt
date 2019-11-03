package com.codekk.mvp.presenter.impl

import com.codekk.ext.NetFunc
import com.codekk.ext.OpListModel
import com.codekk.ext.OpService
import com.codekk.mvp.presenter.OpListPresenter
import com.codekk.mvp.view.OpListView
import com.codekk.ui.base.BasePresenterImpl
import io.reactivex.network.RxNetWork
import io.reactivex.network.cancelTag
import io.reactivex.network.getApi

/**
 * by y on 2017/5/16
 */
class OpListPresenterImpl(view: OpListView) : BasePresenterImpl<OpListView, OpListModel>(view), OpListPresenter {

    override fun netWorkRequest(page: Int) {
        RxNetWork
                .observable(OpService::class.java)
                .getOpList(page)
                .cancelTag(javaClass.simpleName)
                .map(NetFunc())
                .getApi(javaClass.simpleName, this)
    }

    override fun onDestroy() {
        super.onDestroy()
        RxNetWork.cancelTag(javaClass.simpleName)
    }

    override fun onNetWorkSuccess(data: OpListModel) {
        if (data.opList.isEmpty()) {
            view?.noMore()
        } else {
            super.onNetWorkSuccess(data)
        }
    }
}
