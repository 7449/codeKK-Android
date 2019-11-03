package com.codekk.ui.activity

import android.os.Bundle
import android.text.Html
import android.text.TextUtils
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.codekk.R
import com.codekk.mvp.model.RecommendSearchModel
import com.codekk.mvp.presenter.RecommendSearchPresenterImpl
import com.codekk.mvp.view.ViewManager
import com.codekk.ui.base.BaseStatusActivity
import com.codekk.utils.UIUtils
import com.codekk.widget.LoadMoreRecyclerView
import com.status.layout.StatusLayout
import com.xadapter.OnXBindListener
import com.xadapter.adapter.XRecyclerViewAdapter
import com.xadapter.holder.XViewHolder
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_recommend_search.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * by y on 2017/5/20.
 */

class RecommendSearchActivity : BaseStatusActivity<RecommendSearchPresenterImpl>(), ViewManager.RecommendSearchView, OnXBindListener<RecommendSearchModel.RecommendArrayBean>, LoadMoreRecyclerView.LoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    private var text: String = ""
    private var page = 1
    private lateinit var mAdapter: XRecyclerViewAdapter<RecommendSearchModel.RecommendArrayBean>

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
        mAdapter = XRecyclerViewAdapter()

        recyclerView.adapter = mAdapter
                .setLayoutId(R.layout.item_recommend_list)
                .setOnItemClickListener { _, _, info -> UIUtils.openBrowser(info.url) }
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
        mAdapter.addAllData(entity.recommendArray)
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

    override fun onXBind(holder: XViewHolder, position: Int, recommendArrayBean: RecommendSearchModel.RecommendArrayBean) {
        if (!TextUtils.isEmpty(recommendArrayBean.title)) {
            holder.setTextView(R.id.tv_recommend_title, recommendArrayBean.title)
        }
        val descView = holder.getView<AppCompatTextView>(R.id.tv_recommend_desc)
        descView.visibility = if (TextUtils.isEmpty(recommendArrayBean.desc)) View.GONE else View.VISIBLE
        if (!TextUtils.isEmpty(recommendArrayBean.desc)) {
            descView.text = Html.fromHtml(recommendArrayBean.desc)
        }
    }

    companion object {

        private const val TEXT_KEY = "text"

        fun newInstance(text: String) {
            val bundle = Bundle()
            bundle.putString(TEXT_KEY, text)
            UIUtils.startActivity(RecommendSearchActivity::class.java, bundle)
        }
    }
}
