package com.codekk.ui.fragment

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.codekk.Constant
import com.codekk.R
import com.codekk.databinding.LayoutListBinding
import com.codekk.ext.*
import com.codekk.mvp.presenter.impl.BlogListPresenterImpl
import com.codekk.mvp.view.BlogListView
import com.codekk.ui.activity.OpSearchActivity
import com.codekk.ui.activity.ReadmeActivity
import com.codekk.ui.base.BaseViewBindFragment
import com.codekk.ui.widget.LoadMoreRecyclerView
import com.google.android.flexbox.FlexboxLayout
import com.xadapter.*
import com.xadapter.adapter.XAdapter
import com.xadapter.holder.XViewHolder
import org.jetbrains.anko.support.v4.startActivity

/**
 * by y on 2017/5/19
 */
class BlogListFragment : BaseViewBindFragment<LayoutListBinding, BlogListPresenterImpl>(), SwipeRefreshLayout.OnRefreshListener, BlogListView, LoadMoreRecyclerView.LoadMoreListener {

    private val mAdapter by lazy { XAdapter<BlogListBean>() }

    override fun initViewBind(): LayoutListBinding {
        return LayoutListBinding.inflate(layoutInflater)
    }

    override fun initPresenter(): BlogListPresenterImpl {
        return BlogListPresenterImpl(this)
    }

    override fun initActivityCreated() {
        viewBind.recyclerView.setHasFixedSize(true)
        viewBind.recyclerView.layoutManager = LinearLayoutManager(activity)
        viewBind.recyclerView.setLoadingListener(this) //一页显示，不用分页

        viewBind.recyclerView.adapter = mAdapter
                .setItemLayoutId(R.layout.item_blog_list)
                .setOnItemClickListener { _, _, info ->
                    startActivity<ReadmeActivity>(
                            ReadmeActivity.KEY to arrayOf(info.id, info.authorName),
                            ReadmeActivity.TYPE to Constant.TYPE_BLOG
                    )
                }
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

    override fun onRefresh() {
        page = 1
        mStatusView.success()
        mPresenter.netWorkRequest(page)
    }

    override fun onLoadMore() {
        if (viewBind.refreshLayout.isRefreshing) {
            return
        }
        mPresenter.netWorkRequest(page)
    }

    override fun showProgress() {
        viewBind.refreshLayout.isRefreshing = true
    }

    override fun hideProgress() {
        viewBind.refreshLayout.isRefreshing = false
    }

    override fun netWorkSuccess(entity: BlogListModel) {
        if (page == 1) {
            mAdapter.removeAll()
        }
        page += 1
        mAdapter.addAll(entity.blogList)
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

    private fun onXBind(holder: XViewHolder, summaryArrayBean: BlogListBean) {
        holder.setText(R.id.tv_blog_title, summaryArrayBean.title)
        holder.setText(R.id.tv_blog_summary, summaryArrayBean.summary)
        val flexboxLayout = holder.findById<FlexboxLayout>(R.id.fl_box)
        if (holder.getContext().blogTagBoolean()) {
            flexboxLayout.visibility = View.VISIBLE
            flexboxLayout.tags(summaryArrayBean.tagList) {
                startActivity<OpSearchActivity>(OpSearchActivity.TEXT_KEY to it)
            }
        } else {
            flexboxLayout.visibility = View.GONE
        }
    }

    override fun onBusNext(entity: Any) {
        mAdapter.notifyDataSetChanged()
    }
}
