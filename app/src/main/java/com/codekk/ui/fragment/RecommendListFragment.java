package com.codekk.ui.fragment;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.codekk.R;
import com.codekk.data.Constant;
import com.codekk.mvp.model.RecommendListModel;
import com.codekk.mvp.presenter.RecommendListPresenterImpl;
import com.codekk.mvp.view.ViewManager;
import com.codekk.ui.activity.ReadmeActivity;
import com.codekk.ui.activity.RecommendSearchActivity;
import com.codekk.ui.base.BaseStatusFragment;
import com.codekk.utils.MaterialDialogUtils;
import com.codekk.utils.UIUtils;
import com.common.widget.LoadMoreRecyclerView;
import com.common.widget.StatusLayout;
import com.xadapter.OnXBindListener;
import com.xadapter.adapter.XRecyclerViewAdapter;
import com.xadapter.holder.XViewHolder;

import butterknife.BindView;

/**
 * by y on 2017/5/18
 */

public class RecommendListFragment extends BaseStatusFragment<RecommendListPresenterImpl>
        implements ViewManager.RecommendListView, SwipeRefreshLayout.OnRefreshListener,
        LoadMoreRecyclerView.LoadMoreListener,
        OnXBindListener<RecommendListModel.RecommendArrayBean>, MaterialDialogUtils.SearchDialogListener {

    @BindView(R.id.recyclerView)
    LoadMoreRecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout mRefresh;
    private XRecyclerViewAdapter<RecommendListModel.RecommendArrayBean> mAdapter;

    public static RecommendListFragment newInstance() {
        return new RecommendListFragment();
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
                        .setLayoutId(R.layout.item_recommend_list)
                        .setOnItemClickListener((view, position, info) -> ReadmeActivity.newInstance(new String[]{"0", info.getTitle(), info.getUrl()}, Constant.TYPE_RECOMMEND))
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.open_search:
                MaterialDialogUtils.openSearch(R.string.search_recommend_hint, this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected RecommendListPresenterImpl initPresenter() {
        return new RecommendListPresenterImpl(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recommend_list;
    }

    @Override
    public void onRefresh() {
        setStatusViewStatus(StatusLayout.SUCCESS);
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
    public void netWorkSuccess(RecommendListModel recommendListModel) {
        if (mStatusView != null) {
            if (page == 1) {
                mAdapter.removeAll();
            }
            ++page;
            mAdapter.addAllData(recommendListModel.getRecommendArray());
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
    public void onXBind(XViewHolder holder, int position, RecommendListModel.RecommendArrayBean recommendArrayBean) {
        if (!TextUtils.isEmpty(recommendArrayBean.getTitle())) {
            holder.setTextView(R.id.tv_recommend_title, recommendArrayBean.getTitle());
        }
        AppCompatTextView descView = holder.getView(R.id.tv_recommend_desc);
        descView.setVisibility(TextUtils.isEmpty(recommendArrayBean.getDesc()) ? View.GONE : View.VISIBLE);
        if (!TextUtils.isEmpty(recommendArrayBean.getDesc())) {
            descView.setText(Html.fromHtml(recommendArrayBean.getDesc()));
        }
    }

    @Override
    public Activity getSearchActivity() {
        return getActivity();
    }

    @Override
    public void onSearchNext(@NonNull String text) {
        RecommendSearchActivity.newInstance(text);
    }

    @Override
    public void onBusNext(Object o) {

    }
}
