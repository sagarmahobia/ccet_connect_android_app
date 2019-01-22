package com.sagar.ccetmobileapp.activities.notices;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;

import com.sagar.ccetmobileapp.activities.notices.adapter.NoticeCard;
import com.sagar.ccetmobileapp.network.interactors.CCETRepositoryInteractor;
import com.sagar.ccetmobileapp.network.models.serverentities.Notice;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by SAGAR MAHOBIA on 14-Nov-18. at 12:06
 */

@NoticeActivityScope
public class Presenter implements Contract.Presenter {

    private Contract.View view;
    private CCETRepositoryInteractor interactor;

    private CompositeDisposable disposable;
    private List<Notice> notices;

    @Inject
    public Presenter(Contract.View view, CCETRepositoryInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        disposable = new CompositeDisposable();
        load();
    }

    public void load() {
        view.showProgress();
        disposable.add(interactor.
                getNoticesSingle().
                subscribe(notices -> {
                    List<Notice> notices1 = notices.getNotices();
                    if (!notices1.isEmpty()) {
                        this.notices = notices1;
                        view.hideProgress();
                        view.hideMessage();
                        view.showNotices();
                    } else {
                        view.hideProgress();
                        view.hideNotices();
                        view.showMessage("No notice available");
                    }
                }, error -> {
                    view.hideProgress();
                    view.hideNotices();
                    view.showMessage("Something went wrong");
                })
        );
    }

    @Override
    public void onBindNotice(NoticeCard noticeCard, int position) {
        noticeCard.setTitle(notices.get(position).getTitle());
    }

    @Override
    public int getNoticesCardSize() {
        return notices != null ? notices.size() : 0;
    }

    @Override
    public void onNoticeItemClicked(int position) {
        String link = notices.get(position).getLink();
        view.startNoticeViewerActivity(link);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        disposable.dispose();
    }

}
