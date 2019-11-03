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
import com.codekk.ext.*
import com.codekk.mvp.presenter.impl.OpaPresenterImpl
import com.codekk.mvp.view.OpaListView
import com.codekk.ui.base.BaseActivity
import com.codekk.ui.widget.LoadMoreRecyclerView
import com.google.android.flexbox.FlexboxLayout
import com.xadapter.*
import com.xadapter.adapter.XAdapter
import com.xadapter.holder.XViewHolder
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.layout_list.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import org.jetbrains.anko.startActivity

/**
 * by y on 2017/5/18.
 */
class OpaSearchActivity : BaseActivity<OpaPresenterImpl>(R.layout.layout_list), OpaListView, LoadMoreRecyclerView.LoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    private var text: String = ""
    private var page = 1

    private lateinit var mAdapter: XAdapter<OpaListBean>

    override fun initCreate(savedInstanceState: Bundle?) {
        toolbar.title = text
        setSupportActionBar(toolbar)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter = XAdapter()

        recyclerView.adapter = mAdapter
                .setItemLayoutId(R.layout.item_opa_list)
                .setOnItemClickListener { _, _, info ->
                    startActivity<ReadmeActivity>(
                            ReadmeActivity.TYPE to arrayOf(info.id, info.projectName, info.projectUrl),
                            ReadmeActivity.KEY to Constant.TYPE_OPA
                    )
                }
                .setOnBind { holder, _, entity -> onXBind(holder, entity) }

        refreshLayout.setOnRefreshListener(this)
        refreshLayout.post { this.onRefresh() }
    }

    override fun initBundle(bundle: Bundle) {
        super.initBundle(bundle)
        text = bundle.getString(TEXT_KEY, "")
    }

    override fun clickNetWork() {
        super.clickNetWork()
        if (!refreshLayout.isRefreshing) {
            onRefresh()
        }
    }

    override fun initPresenter(): OpaPresenterImpl? {
        return OpaPresenterImpl(this)
    }

    override fun showProgress() {
        refreshLayout.isRefreshing = true
    }

    override fun hideProgress() {
        refreshLayout.isRefreshing = false
    }

    override fun onRefresh() {
        statusLayout.success()
        mPresenter?.netWorkRequestSearch(text, page = 1)
    }

    override fun onLoadMore() {
        if (refreshLayout.isRefreshing) {
            return
        }
        mPresenter?.netWorkRequestSearch(text, page)
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
            statusLayout.error()
            mAdapter.removeAll()
        } else {
            statusLayout.snackBar(R.string.net_error)
        }
    }

    override fun noMore() {
        if (page == 1) {
            statusLayout.empty()
            mAdapter.removeAll()
        } else {
            statusLayout.snackBar(R.string.data_empty)
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
