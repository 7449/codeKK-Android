package com.codekk.mvp.presenter;

import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;

/**
 * by y on 2017/5/23.
 */

public interface PresenterManager {

    interface BlogListPresenter {
        void netWorkRequest(int page);
    }

    interface JobListPresenter {
        void netWorkRequest(int page);
    }

    interface MainPresenter {
        void switchId(@MenuRes int menuRes);

        void onBackPressed();

        void onMainDestroy();
    }

    interface OpaListPresenter {
        void netWorkRequest(int page);
    }

    interface OpaSearchPresenter {
        void netWorkRequest(@NonNull String text, int page);
    }

    interface OpDetailPresenter {
        void netWorkRequest(@NonNull String id, int type);
    }

    interface OpListPresenter {
        void netWorkRequest(int page);
    }

    interface OpSearchPresenter {
        void netWorkRequest(@NonNull String text, int page);
    }

    interface RecommendListPresenter {
        void netWorkRequest(int page);
    }

    interface RecommendSearchPresenter {
        void netWorkRequest(@NonNull String name, int page);
    }

}
