package com.codekk.mvp.presenter


import com.codekk.mvp.model.OpSearchModel
import com.codekk.mvp.view.ViewManager
import com.codekk.net.Api
import com.codekk.net.NetFunc
import com.codekk.ui.base.BasePresenterImpl
import io.reactivex.network.RxNetWork


/**
 * by y on 2017/5/17
 */

class OpSearchPresenterImpl(view: ViewManager.OpSearchView) : BasePresenterImpl<ViewManager.OpSearchView, OpSearchModel>(view), PresenterManager.OpSearchPresenter {

    override fun netWorkRequest(text: String, page: Int) {
        netWork(RxNetWork
                .observable(Api.OpService::class.java)
                .getOpSearch(text, page)
                .map(NetFunc()))
    }

    override fun onNetWorkSuccess(data: OpSearchModel) {
        if (data.projectArray.isEmpty()) {
            view?.noMore()
        } else {
            super.onNetWorkSuccess(data)
        }
    }
}
