package com.codekk.ui.fragment

import android.text.TextUtils
import android.text.util.Linkify
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.os.bundleOf
import androidx.core.text.parseAsHtml
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.codekk.Constant
import com.codekk.R
import com.codekk.databinding.LayoutListBinding
import com.codekk.ext.*
import com.codekk.mvp.presenter.impl.OpPresenterImpl
import com.codekk.mvp.view.OpListView
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
 * by y on 2017/5/16
 */
class OpListFragment : BaseViewBindFragment<LayoutListBinding, OpPresenterImpl>(), SwipeRefreshLayout.OnRefreshListener, OpListView, LoadMoreRecyclerView.LoadMoreListener {

    companion object {
        fun get(text: String): OpListFragment {
            return OpListFragment().apply {
                arguments = bundleOf(
                        OpSearchActivity.TEXT_KEY to text
                )
            }
        }
    }

    private val mAdapter by lazy { XAdapter<OpListBean>() }
    private val searchText by lazy { arguments?.getString(OpSearchActivity.TEXT_KEY, "").orEmpty() }

    override fun initViewBind(): LayoutListBinding {
        return LayoutListBinding.inflate(layoutInflater)
    }

    override fun initPresenter(): OpPresenterImpl = OpPresenterImpl(this)

    override fun initActivityCreated() {
        setHasOptionsMenu(true)
        viewBind.recyclerView.setHasFixedSize(true)
        viewBind.recyclerView.layoutManager = LinearLayoutManager(activity)
        viewBind.recyclerView.setLoadingListener(this)

        viewBind.recyclerView.adapter = mAdapter
                .setItemLayoutId(R.layout.item_op_list)
                .setOnItemClickListener { _, _, info ->
                    startActivity<ReadmeActivity>(ReadmeActivity.KEY to arrayOf(info.id, info.projectName, info.projectUrl), ReadmeActivity.TYPE to Constant.TYPE_OP)
                }
                .setOnBind { holder, _, entity -> onXBind(holder, entity) }

        viewBind.refreshLayout.setOnRefreshListener(this)
        viewBind.refreshLayout.post { this.onRefresh() }
        activity?.findViewById<View>(R.id.toolbar)?.setOnClickListener { viewBind.recyclerView.smoothScrollToPosition(0) }
    }

    override fun clickNetWork() {
        super.clickNetWork()
        if (!viewBind.refreshLayout.isRefreshing) {
            onRefresh()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (searchText.isEmpty()) {
            inflater.inflate(R.menu.search_menu, menu)
        }
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

    override fun onRefresh() {
        mStatusView.success()
        page = 1
        if (searchText.isEmpty()) {
            mPresenter.netWorkRequestList(page)
        } else {
            mPresenter.netWorkRequestSearch(searchText, page)
        }
    }

    override fun onLoadMore() {
        if (viewBind.refreshLayout.isRefreshing) {
            return
        }
        if (searchText.isEmpty()) {
            mPresenter.netWorkRequestList(page)
        } else {
            mPresenter.netWorkRequestSearch(searchText, page)
        }
    }

    override fun showProgress() {
        viewBind.refreshLayout.isRefreshing = true
    }

    override fun hideProgress() {
        viewBind.refreshLayout.isRefreshing = false
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
        textView.text = if (descEmpty) "" else projectArrayBean.desc.parseAsHtml()
        val tagLayout = holder.findById<FlexboxLayout>(R.id.fl_box)
        if (holder.getContext().opTagBoolean()) {
            tagLayout.visibility = View.VISIBLE
            tagLayout.tags(projectArrayBean.tags()) { startActivity<OpSearchActivity>(OpSearchActivity.TEXT_KEY to it) }
        } else {
            tagLayout.visibility = View.GONE
        }
    }

    override fun onBusNext(entity: Any) {
        mAdapter.notifyDataSetChanged()
    }

}
