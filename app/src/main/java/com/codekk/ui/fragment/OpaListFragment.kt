package com.codekk.ui.fragment

import android.text.TextUtils
import android.text.util.Linkify
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.codekk.Constant
import com.codekk.R
import com.codekk.mvp.model.OpaListModel
import com.codekk.mvp.presenter.OpaListPresenterImpl
import com.codekk.mvp.view.ViewManager
import com.codekk.ui.activity.OpSearchActivity
import com.codekk.ui.activity.ReadmeActivity
import com.codekk.ui.base.BaseStatusFragment
import com.codekk.utils.MaterialDialogUtils
import com.codekk.utils.UIUtils
import com.common.util.SPUtils
import com.common.widget.FlowText
import com.common.widget.LoadMoreRecyclerView
import com.google.android.flexbox.FlexboxLayout
import com.status.layout.StatusLayout
import com.xadapter.OnXBindListener
import com.xadapter.adapter.XRecyclerViewAdapter
import com.xadapter.holder.XViewHolder
import kotlinx.android.synthetic.main.fragment_opa_list.*

/**
 * by y on 2017/5/18
 */

class OpaListFragment : BaseStatusFragment<OpaListPresenterImpl>(), ViewManager.OpaListView, SwipeRefreshLayout.OnRefreshListener, LoadMoreRecyclerView.LoadMoreListener, OnXBindListener<OpaListModel.SummaryArrayBean> {

    private lateinit var mAdapter: XRecyclerViewAdapter<OpaListModel.SummaryArrayBean>

    override val layoutId: Int = R.layout.fragment_opa_list

    override fun initActivityCreated() {
        setHasOptionsMenu(true)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setLoadingListener(this)  // 太少了,注掉上拉加载
        mAdapter = XRecyclerViewAdapter()
        recyclerView.adapter = mAdapter
                .setLayoutId(R.layout.item_opa_list)
                .setOnItemClickListener { _, _, info -> ReadmeActivity.newInstance(arrayOf(info._id, info.projectName, info.projectUrl), Constant.TYPE_OPA) }
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

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.search_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        activity?.let {
            return when (item?.itemId) {
                R.id.open_search -> {
                    MaterialDialogUtils.openSearch(it, R.string.search_opa_hint) { s -> OpSearchActivity.newInstance(s) }
                    return true
                }
                else -> super.onOptionsItemSelected(item)
            }
        } ?: return super.onOptionsItemSelected(item)
    }

    override fun initPresenter(): OpaListPresenterImpl? {
        return OpaListPresenterImpl(this)
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

    override fun netWorkSuccess(entity: OpaListModel) {
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


    override fun onXBind(holder: XViewHolder, position: Int, summaryArrayBean: OpaListModel.SummaryArrayBean) {
        holder.setTextView(R.id.tv_project_name, TextUtils.concat("项目名称：", summaryArrayBean.title))
        holder.setTextView(R.id.tv_summary, summaryArrayBean.summary)

        val projectUrl = holder.getView<AppCompatTextView>(R.id.tv_project_url)
        projectUrl.autoLinkMask = if (SPUtils.getBoolean(SPUtils.IS_OPA_URL_WEB, true)) Linkify.WEB_URLS else 0
        projectUrl.visibility = if (TextUtils.isEmpty(summaryArrayBean.projectUrl)) View.GONE else View.VISIBLE
        projectUrl.text = summaryArrayBean.projectUrl

        summaryArrayBean.tagList ?: return

        val flexboxLayout = holder.getView<FlexboxLayout>(R.id.fl_box)
        if (SPUtils.getBoolean(SPUtils.IS_OPA_TAG, true)) {
            flexboxLayout.visibility = View.VISIBLE
            summaryArrayBean.tagList?.let {
                initTags(it, flexboxLayout)
            }
        } else {
            flexboxLayout.visibility = View.GONE
        }
    }

    private fun initTags(tags: List<String>, flexboxLayout: FlexboxLayout) {
        flexboxLayout.removeAllViews()
        val size = tags.size
        for (i in 0 until size) {
            val flowText = FlowText(flexboxLayout.context)
            val tag = tags[i]
            flowText.text = tag
            flexboxLayout.addView(flowText)
            flowText.setOnClickListener { OpSearchActivity.newInstance(tag) }
        }
    }

    override fun onBusNext(entity: Any) {
        mAdapter.notifyDataSetChanged()
    }

    companion object {

        fun newInstance(): OpaListFragment {
            return OpaListFragment()
        }
    }
}
