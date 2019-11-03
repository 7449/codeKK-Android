package com.codekk.mvp.presenter.impl

import com.codekk.Constant
import com.codekk.ext.*
import com.codekk.mvp.presenter.ReadmePresenter
import com.codekk.mvp.view.ReadmeView
import com.codekk.ui.base.BasePresenterImpl
import io.reactivex.network.RxNetWork
import io.reactivex.network.cancelTag
import io.reactivex.network.getApi

/**
 * by y on 2017/5/16
 */
class ReadmePresenterImpl(view: ReadmeView) : BasePresenterImpl<ReadmeView, ReadmeModel>(view), ReadmePresenter {

    override fun netWorkRequest(id: String, type: Int) {
        when (type) {
            Constant.TYPE_JOB -> RxNetWork
                    .observable(JobService::class.java)
                    .getJobDetail(id)
                    .cancelTag(javaClass.simpleName)
                    .map(NetFunc())
                    .getApi(javaClass.simpleName, this)
            Constant.TYPE_OP -> RxNetWork
                    .observable(OpService::class.java)
                    .getOpDetail(id)
                    .cancelTag(javaClass.simpleName)
                    .map(NetFunc())
                    .getApi(javaClass.simpleName, this)
            Constant.TYPE_BLOG -> RxNetWork
                    .observable(BlogService::class.java)
                    .getBlogDetail(id)
                    .cancelTag(javaClass.simpleName)
                    .map(NetFunc())
                    .getApi(javaClass.simpleName, this)
            Constant.TYPE_OPA -> RxNetWork
                    .observable(OpaService::class.java)
                    .getOpaDetail(id)
                    .cancelTag(javaClass.simpleName)
                    .map(NetFunc())
                    .getApi(javaClass.simpleName, this)
            else -> view?.loadWebViewUrl()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        RxNetWork.cancelTag(javaClass.simpleName)
    }
}
