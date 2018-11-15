package com.sagar.ccetmobileapp.activities.notices;

import android.arch.lifecycle.LifecycleObserver;

import com.sagar.ccetmobileapp.activities.notices.adapter.NoticeCard;

/**
 * Created by SAGAR MAHOBIA on 14-Nov-18. at 12:05
 */
public interface Contract {

    interface View {
        void showProgress();

        void hideProgress();

        void showNotices();

        void hideNotices();

        void startNoticeViewerActivity(String link);

        void hideMessage();

        void showMessage(String msg);
    }

    interface Presenter extends LifecycleObserver {

        void load();

        void onBindNotice(NoticeCard noticeCard, int position);

        int getNoticesCardSize();

        void onNoticeItemClicked(int position);

    }

}

