package com.codekk.ui.activity

import android.os.Bundle
import android.text.TextUtils
import android.text.util.Linkify
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.parseAsHtml
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.codekk.Constant
import com.codekk.R
import com.codekk.ext.*
import com.codekk.mvp.presenter.impl.OpSearchPresenterImpl
import com.codekk.mvp.view.OpSearchView
import com.codekk.ui.base.BaseActivity
import com.codekk.ui.widget.LoadMoreRecyclerView
import com.google.android.flexbox.FlexboxLayout
import com.xadapter.*
import com.xadapter.adapter.XAdapter
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import org.jetbrains.anko.startActivity

/**
 * by y on 2017/5/17
 */
class OpSearchActivity : BaseActivity<OpSearchPresenterImpl>(R.layout.activity_search), OpSearchView, LoadMoreRecyclerView.LoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    companion object {
        const val TEXT_KEY = "text"
    }

    private var text: String = ""
    private var page = 1
    private lateinit var mAdapter: XAdapter<OpListBean>

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
        mAdapter = XAdapter()

        recyclerView.adapter = mAdapter
                .setItemLayoutId(R.layout.item_search)
                .setOnItemClickListener { _, _, info ->
                    startActivity<ReadmeActivity>(
                            ReadmeActivity.TYPE to arrayOf(info.id, info.projectName, info.projectUrl),
                            ReadmeActivity.KEY to Constant.TYPE_OP
                    )
                }
                .setOnBind { holder, _, entity ->
                    holder.setText(R.id.tv_author_name, TextUtils.concat("添加者：", entity.authorName))
                    holder.setText(R.id.tv_author_url, TextUtils.concat("个人主页：", entity.authorUrl))
                    holder.setText(R.id.tv_project_name, TextUtils.concat("项目名称：", entity.projectName))
                    val projectUrl = holder.findById<AppCompatTextView>(R.id.tv_project_url)
                    projectUrl.autoLinkMask = if (opUriWebBoolean()) Linkify.WEB_URLS else 0
                    projectUrl.text = entity.projectUrl

                    val textView = holder.findById<AppCompatTextView>(R.id.tv_desc)
                    val descEmpty = TextUtils.isEmpty(entity.desc)
                    textView.visibility = if (descEmpty) View.GONE else View.VISIBLE
                    textView.text = if (descEmpty) "" else entity.desc.parseAsHtml()

                    val flexboxLayout = holder.findById<FlexboxLayout>(R.id.fl_box)
                    if (opTagBoolean()) {
                        flexboxLayout.visibility = View.VISIBLE
                        flexboxLayout.tags(entity.tags()) {
                            startActivity<OpSearchActivity>(TEXT_KEY to it)
                            finish()
                        }
                    } else {
                        flexboxLayout.visibility = View.GONE
                    }
                }

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
        statusLayout.success()
        mPresenter?.netWorkRequest(text, page = 1)
    }

    override fun onLoadMore() {
        if (refreshLayout.isRefreshing) {
            return
        }
        mPresenter?.netWorkRequest(text, page)
    }

    override fun netWorkSuccess(entity: OpListModel) {
        if (page == 1) {
            mAdapter.removeAll()
        }
        ++page
        mAdapter.addAll(entity.opList)
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
}
