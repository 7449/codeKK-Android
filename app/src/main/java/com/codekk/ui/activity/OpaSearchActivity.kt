package com.codekk.ui.activity

import android.os.Bundle
import android.text.TextUtils
import android.text.util.Linkify
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
import com.codekk.ui.base.BaseViewBindActivity
import com.codekk.ui.widget.LoadMoreRecyclerView
import com.google.android.flexbox.FlexboxLayout
import com.xadapter.*
import com.xadapter.adapter.XAdapter
import com.xadapter.holder.XViewHolder
import org.jetbrains.anko.startActivity

/**
 * by y on 2017/5/18.
 */
class OpaSearchActivity : BaseViewBindActivity<LayoutListBinding, OpaPresenterImpl>(), OpaListView, LoadMoreRecyclerView.LoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    private val text by lazy { bundle?.getString(TEXT_KEY, "").orEmpty() }
    private var page = 1

    private val mAdapter by lazy { XAdapter<OpaListBean>() }

    override fun initViewBind(): LayoutListBinding {
        return LayoutListBinding.inflate(layoutInflater)
    }

    override fun initPresenter(): OpaPresenterImpl {
        return OpaPresenterImpl(this)
    }

    override fun initCreate(savedInstanceState: Bundle?) {
        baseViewBind.toolbarRoot.toolbar.title = text
        setSupportActionBar(baseViewBind.toolbarRoot.toolbar)
        viewBind.recyclerView.setHasFixedSize(true)
        viewBind.recyclerView.layoutManager = LinearLayoutManager(this)

        viewBind.recyclerView.adapter = mAdapter
                .setItemLayoutId(R.layout.item_opa_list)
                .setOnItemClickListener { _, _, info ->
                    startActivity<ReadmeActivity>(
                            ReadmeActivity.TYPE to arrayOf(info.id, info.projectName, info.projectUrl),
                            ReadmeActivity.KEY to Constant.TYPE_OPA
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

    override fun showProgress() {
        viewBind.refreshLayout.isRefreshing = true
    }

    override fun hideProgress() {
        viewBind.refreshLayout.isRefreshing = false
    }

    override fun onRefresh() {
        baseViewBind.statusLayout.success()
        mPresenter.netWorkRequestSearch(text, page = 1)
    }

    override fun onLoadMore() {
        if (viewBind.refreshLayout.isRefreshing) {
            return
        }
        mPresenter.netWorkRequestSearch(text, page)
    }

    override fun netWorkSuccess(entity: OpaListModel) {
        if (page == 1) {
            mAdapter.removeAll()
        }
        ++page
        mAdapter.addAll(entity.opaList)
    }

    override fun netWorkError(throwable: Throwable) {
        if (page == 1) {
            baseViewBind.statusLayout.error()
            mAdapter.removeAll()
        } else {
            baseViewBind.statusLayout.snackBar(R.string.net_error)
        }
    }

    override fun noMore() {
        if (page == 1) {
            baseViewBind.statusLayout.empty()
            mAdapter.removeAll()
        } else {
            baseViewBind.statusLayout.snackBar(R.string.data_empty)
        }
    }

    private fun onXBind(holder: XViewHolder, summaryArrayBean: OpaListBean) {
        holder.setText(R.id.tv_project_name, TextUtils.concat("项目名称：", summaryArrayBean.title))
        holder.setText(R.id.tv_summary, summaryArrayBean.summary)

        val projectUrl = holder.findById<AppCompatTextView>(R.id.tv_project_url)
        projectUrl.autoLinkMask = if (opaUriWebBoolean()) Linkify.WEB_URLS else 0
        projectUrl.visibility = if (TextUtils.isEmpty(summaryArrayBean.projectUrl)) View.GONE else View.VISIBLE
        projectUrl.text = summaryArrayBean.projectUrl

        val flexboxLayout = holder.findById<FlexboxLayout>(R.id.fl_box)
        if (opaTagBoolean()) {
            flexboxLayout.visibility = View.VISIBLE
            flexboxLayout.tags(summaryArrayBean.tagList) {
                startActivity<OpSearchActivity>(OpSearchActivity.TEXT_KEY to it)
            }
        } else {
            flexboxLayout.visibility = View.GONE
        }
    }

    companion object {
        private const val TEXT_KEY = "text"
    }
}
