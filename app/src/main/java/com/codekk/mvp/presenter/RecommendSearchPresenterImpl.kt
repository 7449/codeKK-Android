package com.codekk.mvp.presenter


import com.codekk.NetFunc
import com.codekk.RecommendService
import com.codekk.mvp.model.RecommendSearchModel
import com.codekk.mvp.view.ViewManager
import com.codekk.ui.base.BasePresenterImpl
import io.reactivex.network.RxNetWork


/**
 * by y on 2017/5/20.
 */

class RecommendSearchPresenterImpl(view: ViewManager.RecommendSearchView) : BasePresenterImpl<ViewManager.RecommendSearchView, RecommendSearchModel>(view), PresenterManager.RecommendSearchPresenter {

    override fun netWorkRequest(name: String, page: Int) {
        netWork(RxNetWork
                .observable(RecommendService::class.java)
                .getRecommendSearch(name, page)
                .map(NetFunc()))
    }

    override fun onNetWorkSuccess(data: RecommendSearchModel) {
        if (data.recommendArray.isEmpty()) {
            view?.noMore()
        } else {
            super.onNetWorkSuccess(data)
        }
    }
}
