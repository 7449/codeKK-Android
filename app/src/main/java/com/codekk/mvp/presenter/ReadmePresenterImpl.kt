package com.codekk.mvp.presenter


import com.codekk.Constant
import com.codekk.mvp.model.ReadmeModel
import com.codekk.mvp.view.ViewManager
import com.codekk.net.Api
import com.codekk.net.NetFunc
import com.codekk.ui.base.BasePresenterImpl
import io.reactivex.network.RxNetWork


/**
 * by y on 2017/5/16
 */

class ReadmePresenterImpl(view: ViewManager.ReadmeView) : BasePresenterImpl<ViewManager.ReadmeView, ReadmeModel>(view), PresenterManager.OpDetailPresenter {

    override fun netWorkRequest(id: String, type: Int) {
        when (type) {
            Constant.TYPE_JOB -> netWork(RxNetWork
                    .observable(Api.JobService::class.java)
                    .getJobDetail(id)
                    .map(NetFunc()))
            Constant.TYPE_OP -> netWork(RxNetWork
                    .observable(Api.OpService::class.java)
                    .getOpDetail(id)
                    .map(NetFunc()))
            Constant.TYPE_BLOG -> netWork(RxNetWork
                    .observable(Api.BlogService::class.java)
                    .getBlogDetail(id)
                    .map(NetFunc()))
            Constant.TYPE_OPA -> netWork(RxNetWork
                    .observable(Api.OpaService::class.java)
                    .getOpaDetail(id)
                    .map(NetFunc()))
            else -> view?.loadWebViewUrl()
        }
    }
}
