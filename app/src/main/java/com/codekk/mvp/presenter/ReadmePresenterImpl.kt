package com.codekk.mvp.presenter


import com.codekk.*
import com.codekk.mvp.model.ReadmeModel
import com.codekk.mvp.view.ViewManager
import com.codekk.ui.base.BasePresenterImpl
import io.reactivex.network.RxNetWork


/**
 * by y on 2017/5/16
 */

class ReadmePresenterImpl(view: ViewManager.ReadmeView) : BasePresenterImpl<ViewManager.ReadmeView, ReadmeModel>(view), PresenterManager.OpDetailPresenter {

    override fun netWorkRequest(id: String, type: Int) {
        when (type) {
            Constant.TYPE_JOB -> netWork(RxNetWork
                    .observable(JobService::class.java)
                    .getJobDetail(id)
                    .map(NetFunc()))
            Constant.TYPE_OP -> netWork(RxNetWork
                    .observable(OpService::class.java)
                    .getOpDetail(id)
                    .map(NetFunc()))
            Constant.TYPE_BLOG -> netWork(RxNetWork
                    .observable(BlogService::class.java)
                    .getBlogDetail(id)
                    .map(NetFunc()))
            Constant.TYPE_OPA -> netWork(RxNetWork
                    .observable(OpaService::class.java)
                    .getOpaDetail(id)
                    .map(NetFunc()))
            else -> view?.loadWebViewUrl()
        }
    }
}
