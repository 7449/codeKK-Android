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
import com.codekk.databinding.LayoutListBinding
import com.codekk.ext.*
import com.codekk.mvp.presenter.impl.OpaPresenterImpl
import com.codekk.mvp.view.OpaListView
import com.codekk.ui.activity.OpSearchActivity
import com.codekk.ui.activity.ReadmeActivity
import com.codekk.ui.base.BaseViewBindFragment
import com.codekk.ui.widget.LoadMoreRecyclerView
import com.google.android.flexbox.FlexboxLayout
import com.xadapter.*
import com.xadapter.adapter.XAdapter
import com.xadapter.holder.XViewHolder
import org.jetbrains.anko.support.v4.startActivity

/**
 * by y on 2017/5/18
 */
class OpaListFragment : BaseViewBindFragment<LayoutListBinding, OpaPresenterImpl>(), OpaListView, SwipeRefreshLayout.OnRefreshListener, LoadMoreRecyclerView.LoadMoreListener {

    private val mAdapter by lazy { XAdapter<OpaListBean>() }

    override fun initViewBind(): LayoutListBinding {
        return LayoutListBinding.inflate(layoutInflater)
    }

    override fun initPresenter(): OpaPresenterImpl {
        return OpaPresenterImpl(this)
    }

    override fun initActivityCreated() {
        setHasOptionsMenu(true)
        viewBind.recyclerView.setHasFixedSize(true)
        viewBind.recyclerView.layoutManager = LinearLayoutManager(activity)
        viewBind.recyclerView.setLoadingListener(this)  // 太少了,注掉上拉加载
        viewBind.recyclerView.adapter = mAdapter
                .setItemLayoutId(R.layout.item_opa_list)
                .setOnItemClickListener { _, _, info ->
                    startActivity<ReadmeActivity>(
                            ReadmeActivity.KEY to arrayOf(info.id, info.projectName, info.projectUrl),
                            ReadmeActivity.TYPE to Constant.TYPE_OPA
                    )
                }
                .setOnBind { holder, position, entity -> onXBind(holder, entity) }

        viewBind.refreshLayout.setOnRefreshListener(this)
        viewBind.refreshLayout.post { this.onRefresh() }
    }

    override fun clickNetWork() {
        super.clickNetWork()
        if (!viewBind.refreshLayout.isRefreshing) {
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
                    it.openSearch(R.string.search_opa_hint) { s -> startActivity<OpSearchActivity>(OpSearchActivity.TEXT_KEY to s) }
                    return true
                }
                else -> super.onOptionsItemSelected(item)
            }
        } ?: return super.onOptionsItemSelected(item)
    }

    override fun onRefresh() {
        mStatusView.success()
        page = 1
        mPresenter.netWorkRequestList(page)
    }

    override fun onLoadMore() {
        if (viewBind.refreshLayout.isRefreshing) {
            return
        }
        mPresenter.netWorkRequestList(page)
    }

    override fun showProgress() {
        viewBind.refreshLayout.isRefreshing = true
    }

    override fun hideProgress() {
        viewBind.refreshLayout.isRefreshing = false
    }

    override fun netWorkSuccess(entity: OpaListModel) {
        if (page == 1) {
            mAdapter.removeAll()
        }
        page += 1
        mAdapter.addAll(entity.opaList)
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

    private fun onXBind(holder: XViewHolder, summaryArrayBean: OpaListBean) {
        holder.setText(R.id.tv_project_name, TextUtils.concat("项目名称：", summaryArrayBean.title))
        holder.setText(R.id.tv_summary, summaryArrayBean.summary)

        val projectUrl = holder.findById<AppCompatTextView>(R.id.tv_project_url)
        projectUrl.autoLinkMask = if (holder.getContext().opaUriWebBoolean()) Linkify.WEB_URLS else 0
        projectUrl.visibility = if (TextUtils.isEmpty(summaryArrayBean.projectUrl)) View.GONE else View.VISIBLE
        projectUrl.text = summaryArrayBean.projectUrl
        val flexboxLayout = holder.findById<FlexboxLayout>(R.id.fl_box)
        if (holder.getContext().opaTagBoolean()) {
            flexboxLayout.visibility = View.VISIBLE
            flexboxLayout.tags(summaryArrayBean.tagList) {
                startActivity<OpSearchActivity>(OpSearchActivity.TEXT_KEY to it)
            }
        } else {
            flexboxLayout.visibility = View.GONE
        }
    }

    override fun onBusNext(entity: Any) {
        mAdapter.notifyDataSetChanged()
    }

}
