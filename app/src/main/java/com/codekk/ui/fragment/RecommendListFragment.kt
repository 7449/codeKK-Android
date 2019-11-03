package com.codekk.ui.fragment

import android.text.TextUtils
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.parseAsHtml
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.codekk.Constant
import com.codekk.R
import com.codekk.ext.*
import com.codekk.mvp.presenter.impl.RecommendPresenterImpl
import com.codekk.mvp.view.RecommendListView
import com.codekk.ui.activity.ReadmeActivity
import com.codekk.ui.activity.RecommendSearchActivity
import com.codekk.ui.base.BaseFragment
import com.codekk.ui.widget.LoadMoreRecyclerView
import com.xadapter.*
import com.xadapter.adapter.XAdapter
import com.xadapter.holder.XViewHolder
import kotlinx.android.synthetic.main.layout_list.*
import org.jetbrains.anko.support.v4.startActivity

/**
 * by y on 2017/5/18
 */
class RecommendListFragment : BaseFragment<RecommendPresenterImpl>(R.layout.layout_list), RecommendListView, SwipeRefreshLayout.OnRefreshListener, LoadMoreRecyclerView.LoadMoreListener {

    private lateinit var mAdapter: XAdapter<RecommendListBean>

    override fun initActivityCreated() {
        setHasOptionsMenu(true)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setLoadingListener(this)
        mAdapter = XAdapter()
        recyclerView.adapter = mAdapter
                .setItemLayoutId(R.layout.item_recommend_list)
                .setOnItemClickListener { _, _, info ->
                    startActivity<ReadmeActivity>(
                            ReadmeActivity.TYPE to arrayOf("0", info.title, info.url),
                            ReadmeActivity.KEY to Constant.TYPE_RECOMMEND
                    )
                }
                .setOnBind { holder, position, entity -> onXBind(holder, entity) }

        refreshLayout.setOnRefreshListener(this)
        refreshLayout.post { this.onRefresh() }

    }

    override fun clickNetWork() {
        super.clickNetWork()
        if (!refreshLayout.isRefreshing) {
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
                    it.openSearch(R.string.search_recommend_hint) { s ->
                        startActivity<RecommendSearchActivity>(
                                RecommendSearchActivity.TEXT_KEY to s
                        )
                    }
                    return true
                }
                else -> super.onOptionsItemSelected(item)
            }
        } ?: return super.onOptionsItemSelected(item)
    }

    override fun initPresenter(): RecommendPresenterImpl? {
        return RecommendPresenterImpl(this)
    }

    override fun onRefresh() {
        mStatusView.success()
        page = 1
        mPresenter?.netWorkRequestList(page)
    }

    override fun onLoadMore() {
        if (refreshLayout.isRefreshing) {
            return
        }
        mPresenter?.netWorkRequestList(page)
    }

    override fun showProgress() {
        refreshLayout.isRefreshing = true
    }

    override fun hideProgress() {
        refreshLayout.isRefreshing = false
    }

    override fun netWorkSuccess(entity: RecommendListModel) {
        if (page == 1) {
            mAdapter.removeAll()
        }
        page += 1
        mAdapter.addAll(entity.recommendList)
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

    override fun onBusNext(entity: Any) {

    }
}
