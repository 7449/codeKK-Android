package com.codekk.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import com.codekk.R;
import com.codekk.mvp.presenter.MainPresenterImpl;
import com.codekk.mvp.view.ViewManager;
import com.codekk.ui.base.BaseModel;
import com.codekk.ui.base.BaseStatusActivity;
import com.codekk.utils.UIUtils;

import butterknife.BindView;


public class MainActivity extends BaseStatusActivity<MainPresenterImpl>
        implements ViewManager.MainView,
        NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar mToolBar;
    @BindView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.navigationview)
    NavigationView navigationview;
    private long exitTime = 0;

    @Override
    protected void initCreate(@NonNull Bundle savedInstanceState) {
        mToolBar.setTitle(UIUtils.getString(R.string.op_title));
        setSupportActionBar(mToolBar);
        mToolBar.setNavigationIcon(R.drawable.ic_menu);
        mToolBar.setNavigationOnClickListener(v -> mDrawerLayout.openDrawer(Gravity.START));
        navigationview.setNavigationItemSelectedListener(this);
        mPresenter.switchId(MainPresenterImpl.FIRST_FRAGMENT);
    }

    @Override
    protected MainPresenterImpl initPresenter() {
        return new MainPresenterImpl(this);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.notes || itemId == R.id.common) {
            UIUtils.toast(UIUtils.getString(R.string.navigation_tips));
            return false;
        }
        mToolBar.setTitle(item.getTitle());
        mPresenter.switchId(itemId);
        mDrawerLayout.closeDrawers();
        return true;
    }


    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawers();
        } else {
            mPresenter.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mPresenter.switchId(item.getItemId());
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void switchSetting() {
        SettingActivity.newInstance();
    }

    @Override
    public AppCompatActivity getMainActivity() {
        return this;
    }

    @Override
    public void onBack() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            UIUtils.snackBar(mStatusView, R.string.exit_app);
            exitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void selectMenuFirst() {
        MenuItem item = navigationview.getMenu().findItem(R.id.op);
        item.setChecked(true);
        mToolBar.setTitle(item.getTitle());
    }

    @Override
    protected void onDestroy() {
        mPresenter.onMainDestroy();
        super.onDestroy();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void netWorkSuccess(BaseModel baseModel) {

    }

    @Override
    public void netWorkError(Throwable e) {

    }
}
