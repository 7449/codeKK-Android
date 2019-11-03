package com.codekk.mvp.presenter.impl

import com.codekk.ext.JobListModel
import com.codekk.ext.JobService
import com.codekk.ext.NetFunc
import com.codekk.mvp.presenter.JobListPresenter
import com.codekk.mvp.view.JobListView
import com.codekk.ui.base.BasePresenterImpl
import io.reactivex.network.RxNetWork
import io.reactivex.network.cancelTag
import io.reactivex.network.getApi

/**
 * by y on 2017/5/18.
 */
class JobListPresenterImpl(view: JobListView) : BasePresenterImpl<JobListView, JobListModel>(view), JobListPresenter {

    override fun netWorkRequest(page: Int) {
        RxNetWork
                .observable(JobService::class.java)
                .getJobList(page)
                .cancelTag(javaClass.simpleName)
                .map(NetFunc())
                .getApi(javaClass.simpleName, this)
    }

    override fun onDestroy() {
        super.onDestroy()
        RxNetWork.cancelTag(javaClass.simpleName)
    }

    override fun onNetWorkSuccess(data: JobListModel) {
        if (data.jobList.isEmpty()) {
            view?.noMore()
        } else {
            super.onNetWorkSuccess(data)
        }
    }
}
