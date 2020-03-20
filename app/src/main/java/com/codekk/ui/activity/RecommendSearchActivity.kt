package com.codekk.ui.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.parseAsHtml
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.codekk.R
import com.codekk.databinding.LayoutListBinding
import com.codekk.ext.*
import com.codekk.mvp.presenter.impl.RecommendPresenterImpl
import com.codekk.mvp.view.RecommendListView
import com.codekk.ui.base.BaseViewBindActivity
import com.codekk.ui.widget.LoadMoreRecyclerView
import com.xadapter.*
import com.xadapter.adapter.XAdapter
import com.xadapter.holder.XViewHolder
import org.jetbrains.anko.browse

/**
 * by y on 2017/5/20.
 */
class RecommendSearchActivity : BaseViewBindActivity<LayoutListBinding, RecommendPresenterImpl>(), RecommendListView, LoadMoreRecyclerView.LoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    companion object {
        const val TEXT_KEY = "text"
    }

    private val text by lazy { bundle?.getString(TEXT_KEY, "").orEmpty() }
    private val mAdapter by lazy { XAdapter<RecommendListBean>() }
    private var page = 1

    override fun initViewBind(): LayoutListBinding {
        return LayoutListBinding.inflate(layoutInflater)
    }

    override fun initPresenter(): RecommendPresenterImpl {
        return RecommendPresenterImpl(this)
    }

    override fun initCreate(savedInstanceState: Bundle?) {
        baseViewBind.toolbarRoot.toolbar.title = text
        setSupportActionBar(baseViewBind.toolbarRoot.toolbar)

        viewBind.recyclerView.setHasFixedSize(true)
        viewBind.recyclerView.layoutManager = LinearLayoutManager(this)

        viewBind.recyclerView.adapter = mAdapter
                .setItemLayoutId(R.layout.item_recommend_list)
                .setOnItemClickListener { _, _, info -> browse(info.url, true) }
                .setOnBind { holder, _, entity ->
                    onXBind(holder, entity)
                }

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


    override fun netWorkSuccess(entity: RecommendListModel) {
        if (page == 1) {
            mAdapter.removeAll()
        }
        ++page
        mAdapter.addAll(entity.recommendList)
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

    private fun onXBind(holder: XViewHolder, recommendArrayBean: RecommendListBean) {
        if (!TextUtils.isEmpty(recommendArrayBean.title)) {
            holder.setText(R.id.tv_recommend_title, recommendArrayBean.title)
        }
        val descView = holder.findById<AppCompatTextView>(R.id.tv_recommend_desc)
        descView.visibility = if (TextUtils.isEmpty(recommendArrayBean.desc)) View.GONE else View.VISIBLE
        if (!TextUtils.isEmpty(recommendArrayBean.desc)) {
            descView.text = recommendArrayBean.desc.parseAsHtml()
        }
    }

}
