package com.codekk.ui.fragment

import android.text.Html
import android.text.TextUtils
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.status.layout.StatusLayout
import com.codekk.Constant
import com.codekk.R
import com.codekk.mvp.model.RecommendListModel
import com.codekk.mvp.presenter.RecommendListPresenterImpl
import com.codekk.mvp.view.ViewManager
import com.codekk.openSearch
import com.codekk.snackBar
import com.codekk.ui.activity.ReadmeActivity
import com.codekk.ui.activity.RecommendSearchActivity
import com.codekk.ui.base.BaseStatusFragment
import com.codekk.widget.LoadMoreRecyclerView
import com.xadapter.*
import com.xadapter.adapter.XAdapter
import com.xadapter.holder.XViewHolder
import kotlinx.android.synthetic.main.fragment_recommend_list.*
import org.jetbrains.anko.support.v4.startActivity

/**
 * by y on 2017/5/18
 */

class RecommendListFragment : BaseStatusFragment<RecommendListPresenterImpl>(), ViewManager.RecommendListView, SwipeRefreshLayout.OnRefreshListener, LoadMoreRecyclerView.LoadMoreListener {

    private lateinit var mAdapter: XAdapter<RecommendListModel.RecommendArrayBean>

    override val layoutId: Int = R.layout.fragment_recommend_list

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
                .setOnBind { holder, position, entity -> onXBind(holder, position, entity) }

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

    override fun initPresenter(): RecommendListPresenterImpl? {
        return RecommendListPresenterImpl(this)
    }

    override fun onRefresh() {
        setStatusViewStatus(StatusLayout.SUCCESS)
        page = 1
        mPresenter?.netWorkRequest(page)
    }

    override fun onLoadMore() {
        if (refreshLayout.isRefreshing) {
            return
        }
        mPresenter?.netWorkRequest(page)
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
        mAdapter.addAll(entity.recommendArray)
    }

    override fun netWorkError(throwable: Throwable) {
        if (page == 1) {
            setStatusViewStatus(StatusLayout.ERROR)
            mAdapter.removeAll()
        } else {
            mStatusView.snackBar(R.string.net_error)
        }
    }

    override fun noMore() {
        if (page == 1) {
            setStatusViewStatus(StatusLayout.EMPTY)
            mAdapter.removeAll()
        } else {
            mStatusView.snackBar(R.string.data_empty)
        }
    }


    private fun onXBind(holder: XViewHolder, position: Int, recommendArrayBean: RecommendListModel.RecommendArrayBean) {
        if (!TextUtils.isEmpty(recommendArrayBean.title)) {
            holder.setText(R.id.tv_recommend_title, recommendArrayBean.title)
        }
        val descView = holder.findById<AppCompatTextView>(R.id.tv_recommend_desc)
        descView.visibility = if (TextUtils.isEmpty(recommendArrayBean.desc)) View.GONE else View.VISIBLE
        if (!TextUtils.isEmpty(recommendArrayBean.desc)) {
            descView.text = Html.fromHtml(recommendArrayBean.desc)
        }
    }

    override fun onBusNext(entity: Any) {

    }
}
