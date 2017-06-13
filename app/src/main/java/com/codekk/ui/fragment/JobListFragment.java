package com.codekk.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;

import com.codekk.R;
import com.codekk.data.Constant;
import com.codekk.mvp.model.JobListModel;
import com.codekk.mvp.presenter.JobListPresenterImpl;
import com.codekk.mvp.view.ViewManager;
import com.codekk.ui.activity.ReadmeActivity;
import com.codekk.ui.base.BaseStatusFragment;
import com.codekk.utils.UIUtils;
import com.common.widget.LoadMoreRecyclerView;
import com.xadapter.OnXBindListener;
import com.xadapter.adapter.XRecyclerViewAdapter;
import com.xadapter.holder.XViewHolder;

import butterknife.BindView;

/**
 * by y on 2017/5/18.
 */

public class JobListFragment extends BaseStatusFragment<JobListPresenterImpl>
        implements SwipeRefreshLayout.OnRefreshListener, OnXBindListener<JobListModel.SummaryArrayBean>,
        ViewManager.JobListView,
        LoadMoreRecyclerView.LoadMoreListener {

    @BindView(R.id.recyclerView)
    LoadMoreRecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout mRefresh;

    private XRecyclerViewAdapter<JobListModel.SummaryArrayBean> mAdapter;

    public static JobListFragment newInstance() {
        return new JobListFragment();
    }

    @Override
    protected void initActivityCreated() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        mRecyclerView.setLoadingListener(this); //一页显示，不用分页
        mAdapter = new XRecyclerViewAdapter<>();

        mRecyclerView.setAdapter(
                mAdapter
                        .setLayoutId(R.layout.item_job_list)
                        .setOnItemClickListener((view, position, info) ->
                                ReadmeActivity.newInstance(new String[]{info.get_id(), info.getAuthorName()}, Constant.TYPE_JOB))
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
    protected JobListPresenterImpl initPresenter() {
        return new JobListPresenterImpl(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_job_list;
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
    public void netWorkSuccess(JobListModel opListModel) {
        if (mStatusView != null) {
            if (page == 1) {
                mAdapter.removeAll();
            }
            ++page;
            mAdapter.addAllData(opListModel.getSummaryArray());
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
    public void onXBind(XViewHolder holder, int position, JobListModel.SummaryArrayBean summaryArrayBean) {
        holder.setTextView(R.id.tv_job_title, TextUtils.concat(summaryArrayBean.getAuthorName()));
        holder.setTextView(R.id.tv_job_address, TextUtils.concat("地点：", summaryArrayBean.getAuthorCity()));
        holder.setTextView(R.id.tv_job_expiredTime, TextUtils.concat("截止时间：", summaryArrayBean.getExpiredTime()));
        holder.setTextView(R.id.tv_job_summary, TextUtils.concat(summaryArrayBean.getSummary()));
    }

    @Override
    public void onBusNext(Object o) {
        mAdapter.notifyDataSetChanged();
    }
}
