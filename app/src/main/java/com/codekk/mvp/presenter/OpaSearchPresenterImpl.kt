package com.codekk.mvp.presenter


import com.codekk.mvp.model.OpaSearchModel
import com.codekk.mvp.view.ViewManager
import com.codekk.net.Api
import com.codekk.net.NetFunc
import com.codekk.ui.base.BasePresenterImpl
import io.reactivex.network.RxNetWork

/**
 * by y on 2017/5/17
 */

class OpaSearchPresenterImpl(view: ViewManager.OpaSearchView) : BasePresenterImpl<ViewManager.OpaSearchView, OpaSearchModel>(view), PresenterManager.OpaSearchPresenter {

    override fun netWorkRequest(text: String, page: Int) {
        netWork(RxNetWork
                .observable(Api.OpaService::class.java)
                .getOpaSearch(text, page)
                .map(NetFunc()))
    }

    override fun onNetWorkSuccess(data: OpaSearchModel) {
        if (data.summaryArray.isEmpty()) {
            view?.noMore()
        } else {
            super.onNetWorkSuccess(data)
        }
    }
}
