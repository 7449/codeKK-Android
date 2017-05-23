package com.codekk.ui.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.backlayout.SwipeBackActivity;
import com.backlayout.SwipeBackLayout;
import com.codekk.App;
import com.codekk.R;
import com.common.widget.StatusLayout;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * by y on 2017/5/16
 */

public abstract class BaseStatusActivity<P extends BasePresenterImpl> extends SwipeBackActivity {
    private Unbinder bind;
    protected P mPresenter;
    protected Bundle bundle;
    protected StatusLayout mStatusView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStatusView = new StatusLayout(this);
        mStatusView.setSuccessView(getLayoutId(), new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mStatusView.setEmptyView(R.layout.layout_empty_view);
        mStatusView.setErrorView(R.layout.layout_network_error);
        mStatusView.setStatus(StatusLayout.SUCCESS);
        mStatusView.getEmptyView().setOnClickListener(v -> clickNetWork());
        mStatusView.getErrorView().setOnClickListener(v -> clickNetWork());
        setContentView(mStatusView);
        bind = ButterKnife.bind(this);
        mPresenter = initPresenter();
        if (mPresenter != null) {
            mPresenter.setNetWorkTag(getClass().getSimpleName());
        }
        bundle = getIntent().getExtras();
        if (bundle != null) {
            initBundle();
        }
        initCreate(savedInstanceState);

        SwipeBackLayout swipeBackLayout = getSwipeBackLayout();
        swipeBackLayout.setEnableGesture(false); // 暂不开启滑动返回,个人觉得滑动返回和Toolbar的返回键有重复作用.如果开启,要禁止MainActivity右滑返回
        swipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        swipeBackLayout.setEdgeDp(100);
    }

    protected abstract void initCreate(@NonNull Bundle savedInstanceState);

    protected void initBundle() {

    }

    protected abstract P initPresenter();

    protected abstract int getLayoutId();

    protected void clickNetWork() {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
            mPresenter = null;
        }
        App.get(this).watch(this);
        if (bind != null) {
            bind.unbind();
        }
    }
}
