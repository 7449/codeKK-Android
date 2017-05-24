package com.codekk.mvp.presenter;

import android.support.annotation.MenuRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.codekk.R;
import com.codekk.mvp.view.ViewManager;
import com.codekk.ui.base.BaseModel;
import com.codekk.ui.base.BasePresenterImpl;
import com.codekk.ui.fragment.BlogListFragment;
import com.codekk.ui.fragment.JobListFragment;
import com.codekk.ui.fragment.OpListFragment;
import com.codekk.ui.fragment.OpaListFragment;
import com.codekk.ui.fragment.RecommendListFragment;

/**
 * by y on 2017/5/16
 */

public class MainPresenterImpl extends BasePresenterImpl<ViewManager.MainView, BaseModel> implements PresenterManager.MainPresenter {

    private Fragment opFragment, opaFragment, jobFragment, blogFragment, recommendFragment;

    public static final int FIRST_FRAGMENT = -1;

    private static final int TYPE_OP_FRAGMENT = 0;
    private static final String TYPE_OP_TAG = "op";

    private static final int TYPE_OPA_FRAGMENT = 1;
    private static final String TYPE_OPA_TAG = "opa";

    private static final int TYPE_JOB_FRAGMENT = 2;
    private static final String TYPE_JOB_TAG = "job";

    private static final int TYPE_BLOG_FRAGMENT = 3;
    private static final String TYPE_BLOG_TAG = "blog";

    private static final int TYPE_RECOMMEND_FRAGMENT = 4;
    private static final String TYPE_RECOMMEND_TAG = "recommend";

    public MainPresenterImpl(ViewManager.MainView view) {
        super(view);
    }


    @Override
    public void switchId(@MenuRes int menuRes) {
        switch (menuRes) {
            case R.id.op:
            case FIRST_FRAGMENT:
                setSelectFragment(TYPE_OP_FRAGMENT);
                break;
            case R.id.opa:
                setSelectFragment(TYPE_OPA_FRAGMENT);
                break;
            case R.id.job:
                setSelectFragment(TYPE_JOB_FRAGMENT);
                break;
            case R.id.blog:
                setSelectFragment(TYPE_BLOG_FRAGMENT);
                break;
            case R.id.recommend:
                setSelectFragment(TYPE_RECOMMEND_FRAGMENT);
                break;
            case R.id.setting:
                view.switchSetting();
                break;

        }
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = view.getMainActivity().getSupportFragmentManager().findFragmentByTag(TYPE_OP_TAG);
        if (fragment != null && fragment.isHidden()) {
            setSelectFragment(TYPE_OP_FRAGMENT);
            view.selectMenuFirst();
        } else {
            view.onBack();
        }
    }

    @Override
    public void onMainDestroy() {
        if (null != opFragment) {
            opFragment = null;
        }
        if (null != opaFragment) {
            opaFragment = null;
        }
        if (null != jobFragment) {
            jobFragment = null;
        }
        if (null != blogFragment) {
            blogFragment = null;
        }
        if (null != recommendFragment) {
            recommendFragment = null;
        }
    }


    private void setSelectFragment(int type) {
        FragmentManager manager = view.getMainActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        switch (type) {
            case TYPE_OP_FRAGMENT:
                opFragment = manager.findFragmentByTag(TYPE_OP_TAG);
                hideFragment(transaction);
                if (null == opFragment) {
                    opFragment = OpListFragment.newInstance();
                    transaction.add(R.id.fragment, opFragment, TYPE_OP_TAG);
                } else {
                    transaction.show(opFragment);
                }
                break;
            case TYPE_OPA_FRAGMENT:
                opaFragment = manager.findFragmentByTag(TYPE_OPA_TAG);
                hideFragment(transaction);
                if (null == opaFragment) {
                    opaFragment = OpaListFragment.newInstance();
                    transaction.add(R.id.fragment, opaFragment, TYPE_OPA_TAG);
                } else {
                    transaction.show(opaFragment);
                }
                break;
            case TYPE_JOB_FRAGMENT:
                jobFragment = manager.findFragmentByTag(TYPE_JOB_TAG);
                hideFragment(transaction);
                if (null == jobFragment) {
                    jobFragment = JobListFragment.newInstance();
                    transaction.add(R.id.fragment, jobFragment, TYPE_JOB_TAG);
                } else {
                    transaction.show(jobFragment);
                }
                break;
            case TYPE_BLOG_FRAGMENT:
                blogFragment = manager.findFragmentByTag(TYPE_BLOG_TAG);
                hideFragment(transaction);
                if (null == blogFragment) {
                    blogFragment = BlogListFragment.newInstance();
                    transaction.add(R.id.fragment, blogFragment, TYPE_BLOG_TAG);
                } else {
                    transaction.show(blogFragment);
                }
                break;
            case TYPE_RECOMMEND_FRAGMENT:
                recommendFragment = manager.findFragmentByTag(TYPE_RECOMMEND_TAG);
                hideFragment(transaction);
                if (null == recommendFragment) {
                    recommendFragment = RecommendListFragment.newInstance();
                    transaction.add(R.id.fragment, recommendFragment, TYPE_RECOMMEND_TAG);
                } else {
                    transaction.show(recommendFragment);
                }
                break;
        }
        transaction.commit();
    }


    private void hideFragment(FragmentTransaction transaction) {
        if (null != opFragment) {
            transaction.hide(opFragment);
        }
        if (null != opaFragment) {
            transaction.hide(opaFragment);
        }
        if (null != jobFragment) {
            transaction.hide(jobFragment);
        }
        if (null != blogFragment) {
            transaction.hide(blogFragment);
        }
        if (null != recommendFragment) {
            transaction.hide(recommendFragment);
        }
    }
}
