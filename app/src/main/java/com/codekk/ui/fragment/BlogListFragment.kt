package com.codekk.ui.fragment

import android.text.TextUtils
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.status.layout.StatusLayout
import com.codekk.Constant
import com.codekk.R
import com.codekk.blogTagBoolean
import com.codekk.mvp.model.BlogListModel
import com.codekk.mvp.presenter.BlogListPresenterImpl
import com.codekk.mvp.view.ViewManager
import com.codekk.snackBar
import com.codekk.ui.activity.OpSearchActivity
import com.codekk.ui.activity.ReadmeActivity
import com.codekk.ui.base.BaseStatusFragment
import com.codekk.widget.FlowText
import com.codekk.widget.LoadMoreRecyclerView
import com.google.android.flexbox.FlexboxLayout
import com.xadapter.*
import com.xadapter.adapter.XAdapter
import com.xadapter.holder.XViewHolder
import kotlinx.android.synthetic.main.fragment_blog_list.*
import org.jetbrains.anko.support.v4.startActivity

/**
 * by y on 2017/5/19
 */

class BlogListFragment : BaseStatusFragment<BlogListPresenterImpl>(),
        SwipeRefreshLayout.OnRefreshListener, ViewManager.BlogListView, LoadMoreRecyclerView.LoadMoreListener {

    private lateinit var mAdapter: XAdapter<BlogListModel.SummaryArrayBean>

    override val layoutId: Int = R.layout.fragment_blog_list

    override fun initActivityCreated() {
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setLoadingListener(this) //一页显示，不用分页
        mAdapter = XAdapter()

        recyclerView.adapter = mAdapter
                .setItemLayoutId(R.layout.item_blog_list)
                .setOnItemClickListener { _, _, info ->
                    startActivity<ReadmeActivity>(
                            ReadmeActivity.KEY to arrayOf(info._id, info.authorName),
                            ReadmeActivity.TYPE to Constant.TYPE_BLOG
                    )
                }
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

    override fun initPresenter(): BlogListPresenterImpl? {
        return BlogListPresenterImpl(this)
    }

    override fun onRefresh() {
        page = 1
        setStatusViewStatus(StatusLayout.SUCCESS)
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

    override fun netWorkSuccess(entity: BlogListModel) {
        if (page == 1) {
            mAdapter.removeAll()
        }
        page += 1
        mAdapter.addAll(entity.summaryArray)
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

    private fun onXBind(holder: XViewHolder, position: Int, summaryArrayBean: BlogListModel.SummaryArrayBean) {
        holder.setText(R.id.tv_blog_title, summaryArrayBean.title)
        holder.setText(R.id.tv_blog_summary, summaryArrayBean.summary)
        summaryArrayBean.tagList ?: return
        val flexboxLayout = holder.findById<FlexboxLayout>(R.id.fl_box)
        if (holder.getContext().blogTagBoolean()) {
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
            val tag = tags[i]
            if (!TextUtils.isEmpty(tag)) {
                val flowText = FlowText(flexboxLayout.context)
                flowText.text = tag
                flexboxLayout.addView(flowText)
                flowText.setOnClickListener {
                    startActivity<OpSearchActivity>(OpSearchActivity.TEXT_KEY to tag)
                }
            }
        }
    }

    override fun onBusNext(entity: Any) {
        mAdapter.notifyDataSetChanged()
    }
}
