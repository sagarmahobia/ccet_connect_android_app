package com.sagar.ccetmobileapp.activities.host.notices;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.sagar.ccetmobileapp.network.interactors.CCETRepositoryInteractor;
import com.sagar.ccetmobileapp.network.models.serverentities.Notice;
import com.sagar.ccetmobileapp.responsemodel.Response;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by SAGAR MAHOBIA on 23-Jan-19. at 00:37
 */
public class NoticesFragmentViewModel extends ViewModel {

    private CCETRepositoryInteractor interactor;

    private List<Notice> notices;
    private MutableLiveData<Response> responseMutableLiveData;

    private CompositeDisposable disposable;

    NoticesFragmentViewModel(CCETRepositoryInteractor interactor) {
        this.interactor = interactor;

        notices = new ArrayList<>();
        responseMutableLiveData = new MutableLiveData<>();

        disposable = new CompositeDisposable();

        load();
    }

    MutableLiveData<Response> getListResponse() {
        return responseMutableLiveData;
    }

    void load() {
        responseMutableLiveData.setValue(Response.loading());
        disposable.add(interactor.
                getNoticesSingle().
                subscribe(
                        noticeBean -> {
                            List<Notice> notices = noticeBean.getNotices();
                            this.notices.clear();
                            this.notices.addAll(notices);
                            responseMutableLiveData.setValue(Response.success(this.notices)
                            );

                        }, error -> responseMutableLiveData.setValue(Response.error(error))
                )
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        if (disposable != null) {
            disposable.dispose();
        }
    }
}
