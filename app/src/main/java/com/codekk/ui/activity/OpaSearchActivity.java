package com.codekk.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.util.Linkify;
import android.view.View;

import com.codekk.R;
import com.codekk.data.Constant;
import com.codekk.mvp.model.OpaSearchModel;
import com.codekk.mvp.presenter.OpaSearchPresenterImpl;
import com.codekk.mvp.view.ViewManager;
import com.codekk.ui.base.BaseStatusActivity;
import com.codekk.utils.UIUtils;
import com.common.util.SPUtils;
import com.common.widget.FlowText;
import com.common.widget.LoadMoreRecyclerView;
import com.common.widget.StatusLayout;
import com.google.android.flexbox.FlexboxLayout;
import com.xadapter.OnXBindListener;
import com.xadapter.adapter.XRecyclerViewAdapter;
import com.xadapter.holder.XViewHolder;

import java.util.List;

import butterknife.BindView;

/**
 * by y on 2017/5/18.
 */

public class OpaSearchActivity extends BaseStatusActivity<OpaSearchPresenterImpl> implements ViewManager.OpaSearchView,
        OnXBindListener<OpaSearchModel.SummaryArrayBean>,
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

    private XRecyclerViewAdapter<OpaSearchModel.SummaryArrayBean> mAdapter;

    public static void newInstance(@NonNull String text) {
        Bundle bundle = new Bundle();
        bundle.putString(TEXT_KEY, text);
        UIUtils.startActivity(OpaSearchActivity.class, bundle);
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

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.setLoadingListener(this);
        mAdapter = new XRecyclerViewAdapter<>();

        mRecyclerView.setAdapter(
                mAdapter
                        .setLayoutId(R.layout.item_opa_search)
                        .setOnItemClickListener((view, position, info) ->
                                ReadmeActivity.newInstance(new String[]{info.get_id(), info.getProjectName(), info.getProjectUrl()}, Constant.TYPE_OPA))
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
    protected OpaSearchPresenterImpl initPresenter() {
        return new OpaSearchPresenterImpl(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_opa_search;
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
    public void onRefresh() {
        setStatusViewStatus(StatusLayout.SUCCESS);
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
    public void netWorkSuccess(OpaSearchModel opaSearchModel) {
        if (mStatusView != null) {
            if (page == 1) {
                mAdapter.removeAll();
            }
            ++page;
            mAdapter.addAllData(opaSearchModel.getSummaryArray());
        }
    }

    @Override
    public void netWorkError(Throwable e) {
        if (mStatusView != null) {
            if (page == 1) {
                setStatusViewStatus(StatusLayout.ERROR);
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
                setStatusViewStatus(StatusLayout.EMPTY);
                mAdapter.removeAll();
            } else {
                UIUtils.snackBar(mStatusView, R.string.data_empty);
            }
        }
    }

    @Override
    public void onXBind(XViewHolder holder, int position, OpaSearchModel.SummaryArrayBean summaryArrayBean) {
        holder.setTextView(R.id.tv_project_name, TextUtils.concat("项目名称：", summaryArrayBean.getTitle()));
        holder.setTextView(R.id.tv_summary, summaryArrayBean.getSummary());

        AppCompatTextView projectUrl = holder.getView(R.id.tv_project_url);
        projectUrl.setAutoLinkMask(SPUtils.getBoolean(SPUtils.IS_OPA_URL_WEB, true) ? Linkify.WEB_URLS : 0);
        projectUrl.setVisibility(TextUtils.isEmpty(summaryArrayBean.getProjectUrl()) ? View.GONE : View.VISIBLE);
        projectUrl.setText(summaryArrayBean.getProjectUrl());

        FlexboxLayout flexboxLayout = holder.getView(R.id.fl_box);
        if (SPUtils.getBoolean(SPUtils.IS_OPA_TAG, true)) {
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
                FlowText flowText = new FlowText(flexboxLayout.getContext());
                String tag = tags.get(i);
                flowText.setText(tag);
                flexboxLayout.addView(flowText);
                flowText.setOnClickListener(v -> OpSearchActivity.newInstance(tag));
            }
        }
    }
}
