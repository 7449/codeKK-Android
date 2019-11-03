package com.codekk.ui.activity

import android.os.Bundle
import android.text.Html
import android.text.TextUtils
import android.text.util.Linkify
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.codekk.Constant
import com.codekk.R
import com.codekk.mvp.model.OpSearchModel
import com.codekk.mvp.presenter.OpSearchPresenterImpl
import com.codekk.mvp.view.ViewManager
import com.codekk.ui.base.BaseStatusActivity
import com.codekk.utils.SPUtils
import com.codekk.utils.UIUtils
import com.codekk.widget.FlowText
import com.codekk.widget.LoadMoreRecyclerView
import com.google.android.flexbox.FlexboxLayout
import com.status.layout.StatusLayout
import com.xadapter.OnXBindListener
import com.xadapter.adapter.XRecyclerViewAdapter
import com.xadapter.holder.XViewHolder
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * by y on 2017/5/17
 */

class OpSearchActivity : BaseStatusActivity<OpSearchPresenterImpl>(), ViewManager.OpSearchView, LoadMoreRecyclerView.LoadMoreListener, OnXBindListener<OpSearchModel.ProjectArrayBean>, SwipeRefreshLayout.OnRefreshListener {

    private var text: String = ""
    private var page = 1
    private lateinit var mAdapter: XRecyclerViewAdapter<OpSearchModel.ProjectArrayBean>

    override val layoutId: Int = R.layout.activity_search

    override fun initBundle(bundle: Bundle) {
        super.initBundle(bundle)
        text = bundle.getString(TEXT_KEY, "")
    }

    override fun initCreate(savedInstanceState: Bundle?) {
        toolbar.title = text
        setSupportActionBar(toolbar)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setLoadingListener(this)
        mAdapter = XRecyclerViewAdapter()

        recyclerView.adapter = mAdapter
                .setLayoutId(R.layout.item_search)
                .setOnItemClickListener { _, _, info -> ReadmeActivity.newInstance(arrayOf(info._id, info.projectName, info.projectUrl), Constant.TYPE_OP) }
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

    override fun initPresenter(): OpSearchPresenterImpl? {
        return OpSearchPresenterImpl(this)
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

    override fun netWorkSuccess(entity: OpSearchModel) {
        if (page == 1) {
            mAdapter.removeAll()
        }
        ++page
        mAdapter.addAllData(entity.projectArray)
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

    override fun onXBind(holder: XViewHolder, position: Int, projectArrayBean: OpSearchModel.ProjectArrayBean) {
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

    private fun initTags(projectArrayBean: OpSearchModel.ProjectArrayBean, flexboxLayout: FlexboxLayout) {
        flexboxLayout.removeAllViews()
        val tags = projectArrayBean.tags
        tags?.let {
            for (i in 0 until it.size) {
                val tag = it[i].name
                val flowText = FlowText(flexboxLayout.context)
                flowText.text = tag
                flexboxLayout.addView(flowText)
                flowText.setOnClickListener {
                    OpSearchActivity.newInstance(tag)
                    finish()
                }
            }
        }
    }

    companion object {

        private const val TEXT_KEY = "text"

        fun newInstance(text: String) {
            val bundle = Bundle()
            bundle.putString(TEXT_KEY, text)
            UIUtils.startActivity(OpSearchActivity::class.java, bundle)
        }
    }
}
