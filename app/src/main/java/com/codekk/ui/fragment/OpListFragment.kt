package com.codekk.ui.fragment

import android.text.Html
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
import com.codekk.mvp.model.OpListModel
import com.codekk.mvp.presenter.OpListPresenterImpl
import com.codekk.mvp.view.ViewManager
import com.codekk.ui.activity.OpSearchActivity
import com.codekk.ui.activity.ReadmeActivity
import com.codekk.ui.base.BaseStatusFragment
import com.codekk.utils.MaterialDialogUtils
import com.codekk.utils.SPUtils
import com.codekk.utils.UIUtils
import com.codekk.widget.FlowText
import com.codekk.widget.LoadMoreRecyclerView
import com.google.android.flexbox.FlexboxLayout
import com.status.layout.StatusLayout
import com.xadapter.OnXBindListener
import com.xadapter.adapter.XRecyclerViewAdapter
import com.xadapter.holder.XViewHolder
import kotlinx.android.synthetic.main.fragment_op_list.*

/**
 * by y on 2017/5/16
 */

class OpListFragment : BaseStatusFragment<OpListPresenterImpl>(), SwipeRefreshLayout.OnRefreshListener, ViewManager.OpListView, LoadMoreRecyclerView.LoadMoreListener, OnXBindListener<OpListModel.ProjectArrayBean> {


    private lateinit var mAdapter: XRecyclerViewAdapter<OpListModel.ProjectArrayBean>

    override val layoutId: Int = R.layout.fragment_op_list

    override fun initActivityCreated() {
        setHasOptionsMenu(true)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setLoadingListener(this)
        mAdapter = XRecyclerViewAdapter()

        recyclerView.adapter = mAdapter
                .setLayoutId(R.layout.item_op_list)
                .setOnItemClickListener { _, _, info -> ReadmeActivity.newInstance(arrayOf(info._id, info.projectName, info.projectUrl), Constant.TYPE_OP) }
                .onXBind(this)

        refreshLayout.setOnRefreshListener(this)
        refreshLayout.post { this.onRefresh() }
        activity?.findViewById<View>(R.id.main_toolbar)?.setOnClickListener { recyclerView.smoothScrollToPosition(0) }
    }

    override fun clickNetWork() {
        super.clickNetWork()
        if (!refreshLayout.isRefreshing) {
            onRefresh()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        activity?.let {
            return when (item.itemId) {
                R.id.open_search -> {
                    MaterialDialogUtils.openSearch(it, R.string.search_op_hint) { s -> OpSearchActivity.newInstance(s) }
                    return true
                }
                else -> super.onOptionsItemSelected(item)
            }
        } ?: return super.onOptionsItemSelected(item)
    }

    override fun initPresenter(): OpListPresenterImpl? {
        return OpListPresenterImpl(this)
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

    override fun netWorkSuccess(entity: OpListModel) {
        if (page == 1) {
            mAdapter.removeAll()
        }
        page += 1
        mAdapter.addAllData(entity.projectArray)
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

    override fun onXBind(holder: XViewHolder, position: Int, projectArrayBean: OpListModel.ProjectArrayBean) {
        holder.setTextView(R.id.tv_author_name, TextUtils.concat("添加者：", projectArrayBean.authorName))
        holder.setTextView(R.id.tv_author_url, TextUtils.concat("个人主页：", projectArrayBean.authorUrl))
        holder.setTextView(R.id.tv_project_name, TextUtils.concat("项目名称：", projectArrayBean.projectName))

        val projectUrl = holder.getView<AppCompatTextView>(R.id.tv_project_url)
        projectUrl.autoLinkMask = if (SPUtils.getBoolean(SPUtils.IS_OP_URL_WEB, true)) Linkify.WEB_URLS else 0
        projectUrl.text = projectArrayBean.projectUrl

        val textView = holder.getView<AppCompatTextView>(R.id.tv_desc)
        val descEmpty = TextUtils.isEmpty(projectArrayBean.desc)
        textView.visibility = if (descEmpty) View.GONE else View.VISIBLE
        textView.text = if (descEmpty) "" else Html.fromHtml(projectArrayBean.desc)
        val flexboxLayout = holder.getView<FlexboxLayout>(R.id.fl_box)
        if (SPUtils.getBoolean(SPUtils.IS_OP_TAG, true)) {
            flexboxLayout.visibility = View.VISIBLE
            initTags(projectArrayBean, flexboxLayout)
        } else {
            flexboxLayout.visibility = View.GONE
        }
    }


    private fun initTags(projectArrayBean: OpListModel.ProjectArrayBean, flexboxLayout: FlexboxLayout) {
        flexboxLayout.removeAllViews()
        val tags = projectArrayBean.tags
        tags?.let {
            for (i in 0 until it.size) {
                val flowText = FlowText(flexboxLayout.context)
                val tag = it[i].name
                flowText.text = tag
                flexboxLayout.addView(flowText)
                flowText.setOnClickListener { OpSearchActivity.newInstance(tag) }
            }
        }
    }

    override fun onBusNext(entity: Any) {
        mAdapter.notifyDataSetChanged()
    }

    companion object {

        fun newInstance(): OpListFragment {
            return OpListFragment()
        }
    }
}
