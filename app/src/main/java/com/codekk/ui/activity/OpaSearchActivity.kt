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
import com.codekk.mvp.model.OpaSearchModel
import com.codekk.mvp.presenter.OpaSearchPresenterImpl
import com.codekk.mvp.view.ViewManager
import com.codekk.ui.base.BaseStatusActivity
import com.codekk.utils.UIUtils
import com.common.util.SPUtils
import com.common.widget.FlowText
import com.common.widget.LoadMoreRecyclerView
import com.google.android.flexbox.FlexboxLayout
import com.status.layout.StatusLayout
import com.xadapter.OnXBindListener
import com.xadapter.adapter.XRecyclerViewAdapter
import com.xadapter.holder.XViewHolder
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_opa_search.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * by y on 2017/5/18.
 */

class OpaSearchActivity : BaseStatusActivity<OpaSearchPresenterImpl>(), ViewManager.OpaSearchView, OnXBindListener<OpaSearchModel.SummaryArrayBean>, LoadMoreRecyclerView.LoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    private var text: String = ""
    private var page = 1

    private lateinit var mAdapter: XRecyclerViewAdapter<OpaSearchModel.SummaryArrayBean>

    override val layoutId: Int = R.layout.activity_opa_search

    override fun initCreate(savedInstanceState: Bundle?) {
        toolbar.title = text
        setSupportActionBar(toolbar)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter = XRecyclerViewAdapter()

        recyclerView.adapter = mAdapter
                .setLayoutId(R.layout.item_opa_search)
                .setOnItemClickListener { _, _, info -> ReadmeActivity.newInstance(arrayOf(info._id, info.projectName, info.projectUrl), Constant.TYPE_OPA) }
                .onXBind(this)

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

    override fun initPresenter(): OpaSearchPresenterImpl? {
        return OpaSearchPresenterImpl(this)
    }

    override fun showProgress() {
        refreshLayout.isRefreshing = true
    }

    override fun hideProgress() {
        refreshLayout.isRefreshing = false
    }

    override fun onRefresh() {
        setStatusViewStatus(StatusLayout.SUCCESS)
        mPresenter?.netWorkRequest(text, page = 1)
    }

    override fun onLoadMore() {
        if (refreshLayout.isRefreshing) {
            return
        }
        mPresenter?.netWorkRequest(text, page)
    }

    override fun netWorkSuccess(entity: OpaSearchModel) {
        if (page == 1) {
            mAdapter.removeAll()
        }
        ++page
        mAdapter.addAllData(entity.summaryArray)
    }

    override fun netWorkError(throwable: Throwable) {
        if (page == 1) {
            setStatusViewStatus(StatusLayout.ERROR)
            mAdapter.removeAll()
        } else {
            UIUtils.snackBar(activity_status_layout, R.string.net_error)
        }
    }

    override fun noMore() {
        if (page == 1) {
            setStatusViewStatus(StatusLayout.EMPTY)
            mAdapter.removeAll()
        } else {
            UIUtils.snackBar(activity_status_layout, R.string.data_empty)
        }
    }

    override fun onXBind(holder: XViewHolder, position: Int, summaryArrayBean: OpaSearchModel.SummaryArrayBean) {
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

    companion object {

        private const val TEXT_KEY = "text"

        fun newInstance(text: String) {
            val bundle = Bundle()
            bundle.putString(TEXT_KEY, text)
            UIUtils.startActivity(OpaSearchActivity::class.java, bundle)
        }
    }
}
