package com.codekk.ui.activity

import android.os.Bundle
import android.text.Html
import android.text.TextUtils
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.status.layout.StatusLayout
import com.codekk.R
import com.codekk.mvp.model.RecommendSearchModel
import com.codekk.mvp.presenter.RecommendSearchPresenterImpl
import com.codekk.mvp.view.ViewManager
import com.codekk.snackBar
import com.codekk.ui.base.BaseStatusActivity
import com.codekk.widget.LoadMoreRecyclerView
import com.xadapter.*
import com.xadapter.adapter.XAdapter
import com.xadapter.holder.XViewHolder
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_recommend_search.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import org.jetbrains.anko.browse

/**
 * by y on 2017/5/20.
 */

class RecommendSearchActivity : BaseStatusActivity<RecommendSearchPresenterImpl>(), ViewManager.RecommendSearchView, LoadMoreRecyclerView.LoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    companion object {
        const val TEXT_KEY = "text"
    }

    private var text: String = ""
    private var page = 1
    private lateinit var mAdapter: XAdapter<RecommendSearchModel.RecommendArrayBean>

    override val layoutId: Int = R.layout.activity_recommend_search

    override fun initBundle(bundle: Bundle) {
        super.initBundle(bundle)
        text = bundle.getString(TEXT_KEY, "")
    }

    override fun initCreate(savedInstanceState: Bundle?) {
        toolbar.title = text
        setSupportActionBar(toolbar)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter = XAdapter()

        recyclerView.adapter = mAdapter
                .setItemLayoutId(R.layout.item_recommend_list)
                .setOnItemClickListener { _, _, info -> browse(info.url, true) }
                .setOnBind { holder, position, entity ->
                    onXBind(holder, position, entity)
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

    override fun initPresenter(): RecommendSearchPresenterImpl? {
        return RecommendSearchPresenterImpl(this)
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


    override fun netWorkSuccess(entity: RecommendSearchModel) {
        if (page == 1) {
            mAdapter.removeAll()
        }
        ++page
        mAdapter.addAll(entity.recommendArray)
    }

    override fun netWorkError(throwable: Throwable) {
        if (page == 1) {
            setStatusViewStatus(StatusLayout.ERROR)
            mAdapter.removeAll()
        } else {
            statusLayout.snackBar(R.string.net_error)
        }
    }

    override fun noMore() {
        if (page == 1) {
            setStatusViewStatus(StatusLayout.EMPTY)
            mAdapter.removeAll()
        } else {
            statusLayout.snackBar(R.string.data_empty)
        }
    }

    private fun onXBind(holder: XViewHolder, position: Int, recommendArrayBean: RecommendSearchModel.RecommendArrayBean) {
        if (!TextUtils.isEmpty(recommendArrayBean.title)) {
            holder.setText(R.id.tv_recommend_title, recommendArrayBean.title)
        }
        val descView = holder.findById<AppCompatTextView>(R.id.tv_recommend_desc)
        descView.visibility = if (TextUtils.isEmpty(recommendArrayBean.desc)) View.GONE else View.VISIBLE
        if (!TextUtils.isEmpty(recommendArrayBean.desc)) {
            descView.text = Html.fromHtml(recommendArrayBean.desc)
        }
    }

}
