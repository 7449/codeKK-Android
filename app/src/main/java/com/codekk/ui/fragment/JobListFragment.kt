package com.codekk.ui.fragment

import android.text.TextUtils
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.status.layout.StatusLayout
import com.codekk.Constant
import com.codekk.R
import com.codekk.mvp.model.JobListModel
import com.codekk.mvp.presenter.JobListPresenterImpl
import com.codekk.mvp.view.ViewManager
import com.codekk.snackBar
import com.codekk.ui.activity.ReadmeActivity
import com.codekk.ui.base.BaseStatusFragment
import com.codekk.widget.LoadMoreRecyclerView
import com.xadapter.*
import com.xadapter.adapter.XAdapter
import com.xadapter.holder.XViewHolder
import kotlinx.android.synthetic.main.fragment_job_list.*
import org.jetbrains.anko.support.v4.startActivity

/**
 * by y on 2017/5/18.
 */

class JobListFragment : BaseStatusFragment<JobListPresenterImpl>(), SwipeRefreshLayout.OnRefreshListener, ViewManager.JobListView, LoadMoreRecyclerView.LoadMoreListener {

    private lateinit var mAdapter: XAdapter<JobListModel.SummaryArrayBean>

    override val layoutId: Int = R.layout.fragment_job_list

    override fun initActivityCreated() {
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setLoadingListener(this) //一页显示，不用分页
        mAdapter = XAdapter()

        recyclerView.adapter = mAdapter
                .setItemLayoutId(R.layout.item_job_list)
                .setOnItemClickListener { _, _, info ->
                    startActivity<ReadmeActivity>(
                            ReadmeActivity.KEY to arrayOf(info._id, info.authorName),
                            ReadmeActivity.TYPE to Constant.TYPE_JOB
                    )
                }
                .setOnBind { holder, position, entity -> onXBind(holder, position, entity) }

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
        mAdapter.addAll(entity.summaryArray)
    }


    override fun netWorkError(throwable: Throwable) {
        if (page == 1) {
            setStatusViewStatus(StatusLayout.ERROR)
            mAdapter.removeAll()
        } else {
            mStatusView.snackBar(R.string.net_error)
        }
    }

    override fun noMore() {
        if (page == 1) {
            setStatusViewStatus(StatusLayout.EMPTY)
            mAdapter.removeAll()
        } else {
            mStatusView.snackBar(R.string.data_empty)
        }
    }

    private fun onXBind(holder: XViewHolder, position: Int, summaryArrayBean: JobListModel.SummaryArrayBean) {
        holder.setText(R.id.tv_job_title, TextUtils.concat(summaryArrayBean.authorName))
        holder.setText(R.id.tv_job_address, TextUtils.concat("地点：", summaryArrayBean.authorCity))
        holder.setText(R.id.tv_job_expiredTime, TextUtils.concat("截止时间：", summaryArrayBean.expiredTime))
        holder.setText(R.id.tv_job_summary, TextUtils.concat(summaryArrayBean.summary))
    }

    override fun onBusNext(entity: Any) {
        mAdapter.notifyDataSetChanged()
    }
}
