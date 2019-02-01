package com.codekk.mvp.presenter

import com.codekk.mvp.model.RecommendListModel
import com.codekk.mvp.view.ViewManager
import com.codekk.net.Api
import com.codekk.net.NetFunc
import com.codekk.ui.base.BasePresenterImpl

import io.reactivex.network.RxNetWork


/**
 * by y on 2017/5/20.
 */

class RecommendListPresenterImpl(view: ViewManager.RecommendListView) : BasePresenterImpl<ViewManager.RecommendListView, RecommendListModel>(view), PresenterManager.RecommendListPresenter {

    override fun netWorkRequest(page: Int) {
        netWork(RxNetWork
                .observable(Api.RecommendService::class.java)
                .getRecommendList(page)
                .map(NetFunc()))
    }

    override fun onNetWorkSuccess(data: RecommendListModel) {
        if (data.recommendArray.isEmpty()) {
            view?.noMore()
        } else {
            super.onNetWorkSuccess(data)
        }
    }
}
