package com.codekk.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.text.util.Linkify;
import android.view.View;

import com.codekk.R;
import com.codekk.data.Constant;
import com.codekk.mvp.model.OpSearchModel;
import com.codekk.mvp.presenter.OpSearchPresenterImpl;
import com.codekk.mvp.view.ViewManager;
import com.codekk.ui.base.BaseStatusActivity;
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
 * by y on 2017/5/17
 */

public class OpSearchActivity extends BaseStatusActivity<OpSearchPresenterImpl>
        implements ViewManager.OpSearchView, LoadMoreRecyclerView.LoadMoreListener,
        OnXBindListener<OpSearchModel.ProjectArrayBean>, SwipeRefreshLayout.OnRefreshListener {

    private static final String TEXT_KEY = "text";
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout mRefresh;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recyclerView)
    LoadMoreRecyclerView mRecyclerView;
    private String text;
    private int page = 1;
    private XRecyclerViewAdapter<OpSearchModel.ProjectArrayBean> mAdapter;

    public static void newInstance(@NonNull String text) {
        Bundle bundle = new Bundle();
        bundle.putString(TEXT_KEY, text);
        UIUtils.startActivity(OpSearchActivity.class, bundle);
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
        mRecyclerView.setLoadingListener(this);
        mAdapter = new XRecyclerViewAdapter<>();

        mRecyclerView.setAdapter(
                mAdapter
                        .setLayoutId(R.layout.item_search)
                        .setOnItemClickListener((view, position, info) ->
                                ReadmeActivity.newInstance(new String[]{info.get_id(), info.getProjectName(), info.getProjectUrl()}, Constant.TYPE_OP))
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
    protected OpSearchPresenterImpl initPresenter() {
        return new OpSearchPresenterImpl(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
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
    public void netWorkSuccess(OpSearchModel opSearchModel) {
        if (mStatusView != null) {
            if (page == 1) {
                mAdapter.removeAll();
            }
            ++page;
            mAdapter.addAllData(opSearchModel.getProjectArray());
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
    public void onXBind(XViewHolder holder, int position, OpSearchModel.ProjectArrayBean projectArrayBean) {
        holder.setTextView(R.id.tv_author_name, TextUtils.concat("添加者：", projectArrayBean.getAuthorName()));
        holder.setTextView(R.id.tv_author_url, TextUtils.concat("个人主页：", projectArrayBean.getAuthorUrl()));
        holder.setTextView(R.id.tv_project_name, TextUtils.concat("项目名称：", projectArrayBean.getProjectName()));

        AppCompatTextView projectUrl = holder.getView(R.id.tv_project_url);
        projectUrl.setAutoLinkMask(SPUtils.getBoolean(SPUtils.IS_OP_URL_WEB, true) ? Linkify.WEB_URLS : 0);
        projectUrl.setText(projectArrayBean.getProjectUrl());

        AppCompatTextView textView = holder.getView(R.id.tv_desc);
        boolean descEmpty = TextUtils.isEmpty(projectArrayBean.getDesc());
        textView.setVisibility(descEmpty ? View.GONE : View.VISIBLE);
        textView.setText(descEmpty ? "" : Html.fromHtml(projectArrayBean.getDesc()));

        FlexboxLayout flexboxLayout = holder.getView(R.id.fl_box);
        if (SPUtils.getBoolean(SPUtils.IS_OP_TAG, true)) {
            flexboxLayout.setVisibility(View.VISIBLE);
            initTags(projectArrayBean, flexboxLayout);
        } else {
            flexboxLayout.setVisibility(View.GONE);
        }
    }

    private void initTags(OpSearchModel.ProjectArrayBean projectArrayBean, FlexboxLayout flexboxLayout) {
        flexboxLayout.removeAllViews();
        List<OpSearchModel.ProjectArrayBean.TagsBean> tags = projectArrayBean.getTags();
        if (tags != null && !tags.isEmpty()) {
            int size = tags.size();
            for (int i = 0; i < size; i++) {
                String tag = projectArrayBean.getTags().get(i).getName();
                FlowText flowText = new FlowText(flexboxLayout.getContext());
                flowText.setText(tag);
                flexboxLayout.addView(flowText);
                flowText.setOnClickListener(v -> {
                    finish();
                    state = Constant.TYPE_FINISH;
                    OpSearchActivity.newInstance(tag);
                });
            }
        }
    }
}
