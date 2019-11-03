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
import com.codekk.ext.*
import com.codekk.mvp.presenter.impl.OpListPresenterImpl
import com.codekk.mvp.view.OpListView
import com.codekk.ui.activity.OpSearchActivity
import com.codekk.ui.activity.ReadmeActivity
import com.codekk.ui.base.BaseFragment
import com.codekk.ui.widget.FlowText
import com.codekk.ui.widget.LoadMoreRecyclerView
import com.google.android.flexbox.FlexboxLayout
import com.xadapter.*
import com.xadapter.adapter.XAdapter
import com.xadapter.holder.XViewHolder
import kotlinx.android.synthetic.main.fragment_op_list.*
import org.jetbrains.anko.support.v4.startActivity

/**
 * by y on 2017/5/16
 */
class OpListFragment : BaseFragment<OpListPresenterImpl>(R.layout.fragment_op_list), SwipeRefreshLayout.OnRefreshListener, OpListView, LoadMoreRecyclerView.LoadMoreListener {

    private lateinit var mAdapter: XAdapter<OpListBean>

    override fun initActivityCreated() {
        setHasOptionsMenu(true)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setLoadingListener(this)
        mAdapter = XAdapter()

        recyclerView.adapter = mAdapter
                .setItemLayoutId(R.layout.item_op_list)
                .setOnItemClickListener { _, _, info ->
                    startActivity<ReadmeActivity>(
                            ReadmeActivity.KEY to arrayOf(info.id, info.projectName, info.projectUrl),
                            ReadmeActivity.TYPE to Constant.TYPE_OP
                    )
                }
                .setOnBind { holder, _, entity -> onXBind(holder, entity) }

        refreshLayout.setOnRefreshListener(this)
        refreshLayout.post { this.onRefresh() }
        activity?.findViewById<View>(R.id.mToolbar)?.setOnClickListener { recyclerView.smoothScrollToPosition(0) }
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
                    it.openSearch(R.string.search_op_hint) { s -> startActivity<OpSearchActivity>(OpSearchActivity.TEXT_KEY to s) }
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
        mStatusView.success()
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
        mAdapter.addAll(entity.opList)
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

    private fun onXBind(holder: XViewHolder, projectArrayBean: OpListBean) {
        holder.setText(R.id.tv_author_name, TextUtils.concat("添加者：", projectArrayBean.authorName))
        holder.setText(R.id.tv_author_url, TextUtils.concat("个人主页：", projectArrayBean.authorUrl))
        holder.setText(R.id.tv_project_name, TextUtils.concat("项目名称：", projectArrayBean.projectName))

        val projectUrl = holder.findById<AppCompatTextView>(R.id.tv_project_url)
        projectUrl.autoLinkMask = if (holder.getContext().opUriWebBoolean()) Linkify.WEB_URLS else 0
        projectUrl.text = projectArrayBean.projectUrl

        val textView = holder.findById<AppCompatTextView>(R.id.tv_desc)
        val descEmpty = TextUtils.isEmpty(projectArrayBean.desc)
        textView.visibility = if (descEmpty) View.GONE else View.VISIBLE
        textView.text = if (descEmpty) "" else Html.fromHtml(projectArrayBean.desc)
        val flexboxLayout = holder.findById<FlexboxLayout>(R.id.fl_box)
        if (holder.getContext().opTagBoolean()) {
            flexboxLayout.visibility = View.VISIBLE
            initTags(projectArrayBean, flexboxLayout)
        } else {
            flexboxLayout.visibility = View.GONE
        }
    }


    private fun initTags(projectArrayBean: OpListBean, flexboxLayout: FlexboxLayout) {
        flexboxLayout.removeAllViews()
        val tags = projectArrayBean.tags
        tags?.let {
            for (element in it) {
                val flowText = FlowText(flexboxLayout.context)
                val tag = element.name
                flowText.text = tag
                flexboxLayout.addView(flowText)
                flowText.setOnClickListener { startActivity<OpSearchActivity>(OpSearchActivity.TEXT_KEY to tag) }
            }
        }
    }

    override fun onBusNext(entity: Any) {
        mAdapter.notifyDataSetChanged()
    }

}
