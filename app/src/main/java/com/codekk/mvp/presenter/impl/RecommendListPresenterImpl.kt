package com.codekk.mvp.presenter.impl

import com.codekk.ext.NetFunc
import com.codekk.ext.RecommendListModel
import com.codekk.ext.RecommendService
import com.codekk.mvp.presenter.RecommendListPresenter
import com.codekk.mvp.view.RecommendListView
import com.codekk.ui.base.BasePresenterImpl
import io.reactivex.network.RxNetWork
import io.reactivex.network.cancelTag
import io.reactivex.network.getApi

/**
 * by y on 2017/5/20.
 */
class RecommendListPresenterImpl(view: RecommendListView) : BasePresenterImpl<RecommendListView, RecommendListModel>(view), RecommendListPresenter {

    override fun netWorkRequest(page: Int) {
        RxNetWork
                .observable(RecommendService::class.java)
                .getRecommendList(page)
                .cancelTag(javaClass.simpleName)
                .map(NetFunc())
                .getApi(javaClass.simpleName, this)
    }

    override fun onDestroy() {
        super.onDestroy()
        RxNetWork.cancelTag(javaClass.simpleName)
    }

    override fun onNetWorkSuccess(data: RecommendListModel) {
        if (data.recommendList.isEmpty()) {
            view?.noMore()
        } else {
            super.onNetWorkSuccess(data)
        }
    }
}
