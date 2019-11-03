package com.codekk.mvp.presenter.impl

import com.codekk.ext.BlogListModel
import com.codekk.ext.BlogService
import com.codekk.ext.NetFunc
import com.codekk.mvp.presenter.BlogListPresenter
import com.codekk.mvp.view.BlogListView
import com.codekk.ui.base.BasePresenterImpl
import io.reactivex.network.RxNetWork
import io.reactivex.network.cancelTag
import io.reactivex.network.getApi

/**
 * by y on 2017/5/19
 */
class BlogListPresenterImpl(view: BlogListView) : BasePresenterImpl<BlogListView, BlogListModel>(view), BlogListPresenter {

    override fun netWorkRequest(page: Int) {
        RxNetWork
                .observable(BlogService::class.java)
                .getBlogList(page)
                .cancelTag(javaClass.simpleName)
                .map(NetFunc())
                .getApi(javaClass.simpleName, this)
    }

    override fun onDestroy() {
        super.onDestroy()
        RxNetWork.cancelTag(javaClass.simpleName)
    }

    override fun onNetWorkSuccess(data: BlogListModel) {
        if (data.blogList.isEmpty()) {
            view?.noMore()
        } else {
            super.onNetWorkSuccess(data)
        }
    }
}
