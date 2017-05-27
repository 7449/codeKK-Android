package com.codekk.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.codekk.R;
import com.codekk.data.Constant;
import com.codekk.mvp.model.ReadmeModel;
import com.codekk.mvp.presenter.ReadmePresenterImpl;
import com.codekk.mvp.view.ViewManager;
import com.codekk.ui.base.BaseStatusActivity;
import com.codekk.utils.UIUtils;
import com.common.widget.SimpleMarkdownView;
import com.common.widget.StatusLayout;

import butterknife.BindView;

/**
 * by y on 2017/5/16
 */

public class ReadmeActivity extends BaseStatusActivity<ReadmePresenterImpl> implements ViewManager.ReadmeView {

    private static final String KEY = "key";
    private static final String TYPE = "type";
    private String[] detail;
    private int type;

    @BindView(R.id.markdownView)
    SimpleMarkdownView markdownView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout mRefresh;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (markdownView != null) {
            markdownView.destroy();
        }
    }

    public static void newInstance(@NonNull String[] detail, int type) {
        Bundle bundle = new Bundle();
        bundle.putStringArray(KEY, detail);
        bundle.putInt(TYPE, type);
        UIUtils.startActivity(ReadmeActivity.class, bundle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.readme_menu, menu);
        if (type == Constant.TYPE_JOB || type == Constant.TYPE_BLOG || type == Constant.TYPE_RECOMMEND) {
            menu.findItem(R.id.open_browser).setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh_url:
                if (markdownView.isLoading()) {
                    markdownView.reload();
                } else {
                    UIUtils.snackBar(mStatusView, R.string.net_loading);
                }
                return true;
            case R.id.open_browser:
                UIUtils.openBrowser(detail[2]);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void initBundle() {
        super.initBundle();
        detail = bundle.getStringArray(KEY);
        type = bundle.getInt(TYPE);
    }

    @Override
    protected void initCreate(@NonNull Bundle savedInstanceState) {
        mRefresh.setEnabled(false);
        mPresenter.netWorkRequest(detail[0], type);
        mToolbar.setTitle(detail[1]);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void clickNetWork() {
        super.clickNetWork();
        if (!mRefresh.isRefreshing()) {
            mPresenter.netWorkRequest(detail[0], type);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK && markdownView.canGoBack()) {
                markdownView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected ReadmePresenterImpl initPresenter() {
        return new ReadmePresenterImpl(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_readme;
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
    public void netWorkSuccess(ReadmeModel opDetailModel) {
        if (mStatusView != null) {
            if (!TextUtils.isEmpty(opDetailModel.getContent())) {
                markdownView.setMarkDownText(opDetailModel.getContent());
                mStatusView.setStatus(StatusLayout.SUCCESS);
            } else {
                mStatusView.setStatus(StatusLayout.EMPTY);
            }
        }
    }

    @Override
    public void netWorkError(Throwable e) {
        if (mStatusView != null) {
            mStatusView.setStatus(StatusLayout.ERROR);
            UIUtils.snackBar(mStatusView, R.string.net_error);
        }
    }

    @Override
    public void loadWebViewUrl() {
        if (markdownView != null) {
            markdownView.loadUrl(detail[2]);
        }
    }
}
