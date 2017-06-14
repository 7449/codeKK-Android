package com.codekk.ui.base;

import android.support.annotation.NonNull;
import android.util.Log;

import com.codekk.data.Constant;

import io.reactivex.Observable;
import io.reactivex.network.manager.RxNetWork;
import io.reactivex.network.manager.RxNetWorkListener;


/**
 * by y on 2017/5/16
 */

public abstract class BasePresenterImpl<V extends BaseView<M>, M> implements RxNetWorkListener<M> {
    public V view;
    private String netWorkTag;

    public BasePresenterImpl(V view) {
        this.view = view;
    }


    @Override
    public void onNetWorkStart() {
        if (view == null) {
            return;
        }
        view.showProgress();
    }

    @Override
    public void onNetWorkError(Throwable e) {
        Log.i(getClass().getSimpleName(), e.toString());
        if (view == null) {
            return;
        }
        view.hideProgress();
        view.netWorkError(e);
    }

    @Override
    public void onNetWorkComplete() {
        if (view == null) {
            return;
        }
        view.hideProgress();
    }

    @Override
    public void onNetWorkSuccess(M data) {
        if (view == null) {
            return;
        }
        view.netWorkSuccess(data);
    }

    protected void netWork(@NonNull Observable<M> observable) {
        cancelNetTag();
        RxNetWork.getInstance().getApi(netWorkTag, observable, this);
    }


    void setNetWorkTag(String netWorkTag) {
        this.netWorkTag = netWorkTag;
    }

    void onDestroy(int state) {
        switch (state) {
            case Constant.TYPE_NO_FINISH:
                cancelNetTag();
                break;
        }
        if (view != null)
            view = null;
    }

    private void cancelNetTag() {
        RxNetWork.getInstance().cancel(netWorkTag);
    }
}
