package com.codekk.ui.fragment;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.text.TextUtils;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.codekk.R;
import com.codekk.data.Constant;
import com.codekk.mvp.model.OpListModel;
import com.codekk.mvp.presenter.OpListPresenterImpl;
import com.codekk.mvp.view.ViewManager;
import com.codekk.ui.activity.OpSearchActivity;
import com.codekk.ui.activity.ReadmeActivity;
import com.codekk.ui.base.BaseStatusFragment;
import com.codekk.utils.MaterialDialogUtils;
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
 * by y on 2017/5/16
 */

public class OpListFragment extends BaseStatusFragment<OpListPresenterImpl>
        implements SwipeRefreshLayout.OnRefreshListener,
        ViewManager.OpListView,
        LoadMoreRecyclerView.LoadMoreListener,
        OnXBindListener<OpListModel.ProjectArrayBean>,
        MaterialDialogUtils.SearchDialogListener {


    @BindView(R.id.recyclerView)
    LoadMoreRecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout mRefresh;

    private XRecyclerViewAdapter<OpListModel.ProjectArrayBean> mAdapter;

    public static OpListFragment newInstance() {
        return new OpListFragment();
    }

    @Override
    protected void initActivityCreated() {
        setHasOptionsMenu(true);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setLoadingListener(this);
        mAdapter = new XRecyclerViewAdapter<>();

        mRecyclerView.setAdapter(
                mAdapter
                        .setLayoutId(R.layout.item_op_list)
                        .setOnItemClickListener((view, position, info) ->
                                ReadmeActivity.newInstance(new String[]{info.get_id(), info.getProjectName(), info.getProjectUrl()}, Constant.TYPE_OP))
                        .onXBind(this)
        );

        mRefresh.setOnRefreshListener(this);
        mRefresh.post(this::onRefresh);
        getActivity().findViewById(R.id.toolbar).setOnClickListener(v -> mRecyclerView.smoothScrollToPosition(0));
    }

    @Override
    protected void clickNetWork() {
        super.clickNetWork();
        if (!mRefresh.isRefreshing()) {
            onRefresh();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.open_search:
                MaterialDialogUtils.openSearch(R.string.search_op_hint, this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected OpListPresenterImpl initPresenter() {
        return new OpListPresenterImpl(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_op_list;
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
    public void netWorkSuccess(OpListModel opListModel) {
        if (mStatusView != null) {
            if (page == 1) {
                mAdapter.removeAll();
            }
            ++page;
            mAdapter.addAllData(opListModel.getProjectArray());
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
    public void onXBind(XViewHolder holder, int position, OpListModel.ProjectArrayBean projectArrayBean) {
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


    private void initTags(OpListModel.ProjectArrayBean projectArrayBean, FlexboxLayout flexboxLayout) {
        flexboxLayout.removeAllViews();
        List<OpListModel.ProjectArrayBean.TagsBean> tags = projectArrayBean.getTags();
        if (tags != null) {
            int size = tags.size();
            for (int i = 0; i < size; i++) {
                FlowText flowText = new FlowText(flexboxLayout.getContext());
                String tag = projectArrayBean.getTags().get(i).getName();
                flowText.setText(tag);
                flexboxLayout.addView(flowText);
                flowText.setOnClickListener(v -> OpSearchActivity.newInstance(tag));

            }
        }
    }

    @Override
    public Activity getSearchActivity() {
        return getActivity();
    }

    @Override
    public void onSearchNext(@NonNull String text) {
        OpSearchActivity.newInstance(text);
    }

    @Override
    public void onBusNext(Object o) {
        mAdapter.notifyDataSetChanged();
    }
}
