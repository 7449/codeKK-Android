package com.codekk.ui.fragment

import android.text.TextUtils
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.codekk.Constant
import com.codekk.R
import com.codekk.mvp.model.JobListModel
import com.codekk.mvp.presenter.JobListPresenterImpl
import com.codekk.mvp.view.ViewManager
import com.codekk.ui.activity.ReadmeActivity
import com.codekk.ui.base.BaseStatusFragment
import com.codekk.utils.UIUtils
import com.codekk.widget.LoadMoreRecyclerView
import com.status.layout.StatusLayout
import com.xadapter.OnXBindListener
import com.xadapter.adapter.XRecyclerViewAdapter
import com.xadapter.holder.XViewHolder
import kotlinx.android.synthetic.main.fragment_job_list.*

/**
 * by y on 2017/5/18.
 */

class JobListFragment : BaseStatusFragment<JobListPresenterImpl>(), SwipeRefreshLayout.OnRefreshListener, OnXBindListener<JobListModel.SummaryArrayBean>, ViewManager.JobListView, LoadMoreRecyclerView.LoadMoreListener {

    private lateinit var mAdapter: XRecyclerViewAdapter<JobListModel.SummaryArrayBean>

    override val layoutId: Int = R.layout.fragment_job_list

    override fun initActivityCreated() {
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setLoadingListener(this) //一页显示，不用分页
        mAdapter = XRecyclerViewAdapter()

        recyclerView.adapter = mAdapter
                .setLayoutId(R.layout.item_job_list)
                .setOnItemClickListener { _, _, info -> ReadmeActivity.newInstance(arrayOf(info._id, info.authorName), Constant.TYPE_JOB) }
                .onXBind(this)

        refreshLayout.setOnRefreshListener(this)
        refreshLayout.post { this.onRefresh() }
    }

    override fun clickNetWork() {
        super.clickNetWork()
        if (!refreshLayout.isRefreshing) {
            onRefresh()
        }
    }

    override fun initPresenter(): JobListPresenterImpl? {
        return JobListPresenterImpl(this)
    }

    override fun onRefresh() {
        setStatusViewStatus(StatusLayout.SUCCESS)
        page = 1
        mPresenter?.netWorkRequest(page)
    }

    override fun onLoadMore() {
        if (refreshLayout.isRefreshing) {
            return
        }
        mPresenter?.netWorkRequest(page)
    }

    override fun showProgress() {
        refreshLayout.isRefreshing = true
    }

    override fun hideProgress() {
        refreshLayout.isRefreshing = false
    }

    override fun netWorkSuccess(entity: JobListModel) {
        if (page == 1) {
            mAdapter.removeAll()
        }
        page += 1
        mAdapter.addAllData(entity.summaryArray)
    }


    override fun netWorkError(throwable: Throwable) {
        if (page == 1) {
            setStatusViewStatus(StatusLayout.ERROR)
            mAdapter.removeAll()
        } else {
            UIUtils.snackBar(mStatusView, R.string.net_error)
        }
    }

    override fun noMore() {
        if (page == 1) {
            setStatusViewStatus(StatusLayout.EMPTY)
            mAdapter.removeAll()
        } else {
            UIUtils.snackBar(mStatusView, R.string.data_empty)
        }
    }

    override fun onXBind(holder: XViewHolder, position: Int, summaryArrayBean: JobListModel.SummaryArrayBean) {
        holder.setTextView(R.id.tv_job_title, TextUtils.concat(summaryArrayBean.authorName))
        holder.setTextView(R.id.tv_job_address, TextUtils.concat("地点：", summaryArrayBean.authorCity))
        holder.setTextView(R.id.tv_job_expiredTime, TextUtils.concat("截止时间：", summaryArrayBean.expiredTime))
        holder.setTextView(R.id.tv_job_summary, TextUtils.concat(summaryArrayBean.summary))
    }

    override fun onBusNext(entity: Any) {
        mAdapter.notifyDataSetChanged()
    }

    companion object {
        fun newInstance(): JobListFragment {
            return JobListFragment()
        }
    }
}
