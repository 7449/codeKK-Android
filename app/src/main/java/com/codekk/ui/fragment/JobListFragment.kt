package com.codekk.ui.fragment

import android.text.TextUtils
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.codekk.Constant
import com.codekk.R
import com.codekk.databinding.LayoutListBinding
import com.codekk.ext.*
import com.codekk.mvp.presenter.impl.JobListPresenterImpl
import com.codekk.mvp.view.JobListView
import com.codekk.ui.activity.ReadmeActivity
import com.codekk.ui.base.BaseViewBindFragment
import com.codekk.ui.widget.LoadMoreRecyclerView
import com.xadapter.*
import com.xadapter.adapter.XAdapter
import com.xadapter.holder.XViewHolder
import org.jetbrains.anko.support.v4.startActivity

/**
 * by y on 2017/5/18.
 */
class JobListFragment : BaseViewBindFragment<LayoutListBinding, JobListPresenterImpl>(), SwipeRefreshLayout.OnRefreshListener, JobListView, LoadMoreRecyclerView.LoadMoreListener {

    private val mAdapter by lazy { XAdapter<JobListBean>() }

    override fun initViewBind(): LayoutListBinding {
        return LayoutListBinding.inflate(layoutInflater)
    }

    override fun initPresenter(): JobListPresenterImpl {
        return JobListPresenterImpl(this)
    }

    override fun initActivityCreated() {
        viewBind.recyclerView.setHasFixedSize(true)
        viewBind.recyclerView.layoutManager = LinearLayoutManager(activity)
        viewBind.recyclerView.setLoadingListener(this)

        viewBind.recyclerView.adapter = mAdapter
                .setItemLayoutId(R.layout.item_job_list)
                .setOnItemClickListener { _, _, info ->
                    startActivity<ReadmeActivity>(
                            ReadmeActivity.KEY to arrayOf(info.id, info.authorName),
                            ReadmeActivity.TYPE to Constant.TYPE_JOB
                    )
                }
                .setOnBind { holder, _, entity -> onXBind(holder, entity) }

        viewBind.refreshLayout.setOnRefreshListener(this)
        viewBind.refreshLayout.post { this.onRefresh() }
    }

    override fun clickNetWork() {
        super.clickNetWork()
        if (!viewBind.refreshLayout.isRefreshing) {
            onRefresh()
        }
    }

    override fun onRefresh() {
        mStatusView.success()
        page = 1
        mPresenter.netWorkRequest(page)
    }

    override fun onLoadMore() {
        if (viewBind.refreshLayout.isRefreshing) {
            return
        }
        mPresenter.netWorkRequest(page)
    }

    override fun showProgress() {
        viewBind.refreshLayout.isRefreshing = true
    }

    override fun hideProgress() {
        viewBind.refreshLayout.isRefreshing = false
    }

    override fun netWorkSuccess(entity: JobListModel) {
        if (page == 1) {
            mAdapter.removeAll()
        }
        page += 1
        mAdapter.addAll(entity.jobList)
    }

    override fun netWorkError(throwable: Throwable) {
        if (page == 1) {
            mStatusView.error()
            mAdapter.removeAll()
        } else {
            mStatusView.snackBar(R.string.net_error)
        }
    }

    override fun noMore() {
        if (page == 1) {
            mStatusView.empty()
            mAdapter.removeAll()
        } else {
            mStatusView.snackBar(R.string.data_empty)
        }
    }

    private fun onXBind(holder: XViewHolder, summaryArrayBean: JobListBean) {
        holder.setText(R.id.tv_job_title, TextUtils.concat(summaryArrayBean.authorName))
        holder.setText(R.id.tv_job_address, TextUtils.concat("地点：", summaryArrayBean.authorCity))
        holder.setText(R.id.tv_job_expiredTime, TextUtils.concat("截止时间：", summaryArrayBean.expiredTime))
        holder.setText(R.id.tv_job_summary, TextUtils.concat(summaryArrayBean.summary))
    }

    override fun onBusNext(entity: Any) {
        mAdapter.notifyDataSetChanged()
    }
}
