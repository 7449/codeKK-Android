package com.codekk.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;

import com.codekk.R;
import com.codekk.mvp.model.RecommendSearchModel;
import com.codekk.mvp.presenter.RecommendSearchPresenterImpl;
import com.codekk.mvp.view.ViewManager;
import com.common.widget.LoadMoreRecyclerView;
import com.common.widget.StatusLayout;
import com.xadapter.OnXBindListener;
import com.xadapter.adapter.XRecyclerViewAdapter;
import com.xadapter.holder.XViewHolder;

import butterknife.BindView;
import com.codekk.ui.base.BaseStatusActivity;
import com.codekk.utils.UIUtils;

/**
 * by y on 2017/5/20.
 */

public class RecommendSearchActivity extends BaseStatusActivity<RecommendSearchPresenterImpl> implements ViewManager.RecommendSearchView,
        OnXBindListener<RecommendSearchModel.RecommendArrayBean>,
        LoadMoreRecyclerView.LoadMoreListener, SwipeRefreshLayout.OnRefreshListener {


    private static final String TEXT_KEY = "text";
    private String text;
    private int page = 1;

    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout mRefresh;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recyclerView)
    LoadMoreRecyclerView mRecyclerView;

    private XRecyclerViewAdapter<RecommendSearchModel.RecommendArrayBean> mAdapter;

    public static void newInstance(@NonNull String text) {
        Bundle bundle = new Bundle();
        bundle.putString(TEXT_KEY, text);
        UIUtils.startActivity(RecommendSearchActivity.class, bundle);
    }

    @Override
    protected void initBundle() {
        super.initBundle();
        text = bundle.getString(TEXT_KEY);
    }

    @Override
    protected void initCreate(@NonNull Bundle savedInstanceState) {
        mToolbar.setTitle(text);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.setLoadingListener(this);
        mAdapter = new XRecyclerViewAdapter<>();

        mRecyclerView.setAdapter(
                mAdapter
                        .setLayoutId(R.layout.item_recommend_list)
                        .setOnItemClickListener((view, position, info) -> UIUtils.openBrowser(info.getUrl()))
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
    protected RecommendSearchPresenterImpl initPresenter() {
        return new RecommendSearchPresenterImpl(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recommend_search;
    }

    public void showProgress() {
        mRefresh.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        mRefresh.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        mStatusView.setStatus(StatusLayout.SUCCESS);
        mPresenter.netWorkRequest(text, page = 1);
    }

    @Override
    public void onLoadMore() {
        if (mRefresh.isRefreshing()) {
            return;
        }
        mPresenter.netWorkRequest(text, page);
    }


    @Override
    public void netWorkSuccess(RecommendSearchModel recommendSearchModel) {
        if (mStatusView != null) {
            if (page == 1) {
                mAdapter.removeAll();
            }
            ++page;
            mAdapter.addAllData(recommendSearchModel.getRecommendArray());
            mStatusView.setStatus(StatusLayout.SUCCESS);
        }
    }

    @Override
    public void netWorkError(Throwable e) {
        if (mStatusView != null) {
            if (page == 1) {
                mAdapter.removeAll();
                mStatusView.setStatus(StatusLayout.ERROR);
            } else {
                UIUtils.snackBar(mStatusView, R.string.net_error);
            }
        }
    }

    @Override
    public void noMore() {
        if (mStatusView != null) {
            if (page == 1) {
                mStatusView.setStatus(StatusLayout.EMPTY);
            } else {
                UIUtils.snackBar(mStatusView, R.string.data_empty);
            }
        }
    }

    @Override
    public void onXBind(XViewHolder holder, int position, RecommendSearchModel.RecommendArrayBean recommendArrayBean) {
        if (!TextUtils.isEmpty(recommendArrayBean.getTitle())) {
            holder.setTextView(R.id.tv_recommend_title, recommendArrayBean.getTitle());
        }
        AppCompatTextView descView = holder.getView(R.id.tv_recommend_desc);
        descView.setVisibility(TextUtils.isEmpty(recommendArrayBean.getDesc()) ? View.GONE : View.VISIBLE);
        if (!TextUtils.isEmpty(recommendArrayBean.getDesc())) {
            descView.setText(Html.fromHtml(recommendArrayBean.getDesc()));
        }
    }
}
