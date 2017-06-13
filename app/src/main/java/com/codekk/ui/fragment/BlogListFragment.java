package com.codekk.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.codekk.R;
import com.codekk.data.Constant;
import com.codekk.mvp.model.BlogListModel;
import com.codekk.mvp.presenter.BlogListPresenterImpl;
import com.codekk.mvp.view.ViewManager;
import com.codekk.ui.activity.OpSearchActivity;
import com.codekk.ui.activity.ReadmeActivity;
import com.codekk.ui.base.BaseStatusFragment;
import com.codekk.utils.UIUtils;
import com.common.util.SPUtils;
import com.common.widget.FlowText;
import com.common.widget.LoadMoreRecyclerView;
import com.google.android.flexbox.FlexboxLayout;
import com.xadapter.OnXBindListener;
import com.xadapter.adapter.XRecyclerViewAdapter;
import com.xadapter.holder.XViewHolder;

import java.util.List;

import butterknife.BindView;

/**
 * by y on 2017/5/19
 */

public class BlogListFragment extends BaseStatusFragment<BlogListPresenterImpl>
        implements SwipeRefreshLayout.OnRefreshListener,
        ViewManager.BlogListView,
        LoadMoreRecyclerView.LoadMoreListener,
        OnXBindListener<BlogListModel.SummaryArrayBean> {


    @BindView(R.id.recyclerView)
    LoadMoreRecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout mRefresh;

    private XRecyclerViewAdapter<BlogListModel.SummaryArrayBean> mAdapter;

    public static BlogListFragment newInstance() {
        return new BlogListFragment();
    }

    @Override
    protected void initActivityCreated() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        mRecyclerView.setLoadingListener(this); //一页显示，不用分页
        mAdapter = new XRecyclerViewAdapter<>();

        mRecyclerView.setAdapter(
                mAdapter
                        .setLayoutId(R.layout.item_blog_list)
                        .setOnItemClickListener((view, position, info) ->
                                ReadmeActivity.newInstance(new String[]{info.get_id(), info.getAuthorName()}, Constant.TYPE_BLOG))
                        .onXBind(this)
        );

        mRefresh.setOnRefreshListener(this);
        mRefresh.post(this::onRefresh);
    }

    @Override
    protected void clickNetWork() {
        super.clickNetWork();
        if (!mRefresh.isRefreshing()) {
            onRefresh();
        }
    }

    @Override
    protected BlogListPresenterImpl initPresenter() {
        return new BlogListPresenterImpl(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_blog_list;
    }

    @Override
    public void onRefresh() {
        mPresenter.netWorkRequest(page = 1);
    }

    @Override
    public void onLoadMore() {
        if (mRefresh.isRefreshing()) {
            return;
        }
        mPresenter.netWorkRequest(page);
    }

    @Override
    public void showProgress() {
        if (mRefresh != null)
            mRefresh.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        if (mRefresh != null)
            mRefresh.setRefreshing(false);
    }

    @Override
    public void netWorkSuccess(BlogListModel blogListModel) {
        if (mStatusView != null) {
            if (page == 1) {
                mAdapter.removeAll();
            }
            ++page;
            mAdapter.addAllData(blogListModel.getSummaryArray());
        }
    }

    @Override
    public void netWorkError(Throwable e) {
        if (mStatusView != null) {
            if (page == 1) {
                mAdapter.removeAll();
            } else {
                UIUtils.snackBar(mStatusView, R.string.net_error);
            }
        }
    }

    @Override
    public void noMore() {
        if (mStatusView != null) {
            if (page == 1) {
                mAdapter.removeAll();
            } else {
                UIUtils.snackBar(mStatusView, R.string.data_empty);
            }
        }
    }

    @Override
    public void onXBind(XViewHolder holder, int position, BlogListModel.SummaryArrayBean summaryArrayBean) {
        holder.setTextView(R.id.tv_blog_title, summaryArrayBean.getTitle());
        holder.setTextView(R.id.tv_blog_summary, summaryArrayBean.getSummary());

        FlexboxLayout flexboxLayout = holder.getView(R.id.fl_box);
        if (SPUtils.getBoolean(SPUtils.IS_BLOG_TAG, true)) {
            flexboxLayout.setVisibility(View.VISIBLE);
            initTags(summaryArrayBean.getTagList(), flexboxLayout);
        } else {
            flexboxLayout.setVisibility(View.GONE);
        }
    }

    private void initTags(List<String> tags, FlexboxLayout flexboxLayout) {
        flexboxLayout.removeAllViews();
        if (tags != null) {
            int size = tags.size();
            for (int i = 0; i < size; i++) {
                String tag = tags.get(i);
                if (!TextUtils.isEmpty(tag)) {
                    FlowText flowText = new FlowText(flexboxLayout.getContext());
                    flowText.setText(tag);
                    flexboxLayout.addView(flowText);
                    flowText.setOnClickListener(v -> OpSearchActivity.newInstance(tag));
                }
            }
        }
    }

    @Override
    public void onBusNext(Object o) {
        mAdapter.notifyDataSetChanged();
    }

}
