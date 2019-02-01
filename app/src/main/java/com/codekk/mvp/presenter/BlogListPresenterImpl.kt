package com.codekk.mvp.presenter

import com.codekk.mvp.model.BlogListModel
import com.codekk.mvp.view.ViewManager
import com.codekk.net.Api
import com.codekk.net.NetFunc
import com.codekk.ui.base.BasePresenterImpl

import io.reactivex.network.RxNetWork


/**
 * by y on 2017/5/19
 */

class BlogListPresenterImpl(view: ViewManager.BlogListView) : BasePresenterImpl<ViewManager.BlogListView, BlogListModel>(view), PresenterManager.BlogListPresenter {

    override fun netWorkRequest(page: Int) {
        netWork(RxNetWork
                .observable(Api.BlogService::class.java)
                .getBlogList(page)
                .map(NetFunc()))
    }


    override fun onNetWorkSuccess(data: BlogListModel) {
        if (data.summaryArray.isEmpty()) {
            view?.noMore()
        } else {
            super.onNetWorkSuccess(data)
        }
    }
}
