package com.sagar.ccetmobileapp.activities.host.notices;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.sagar.ccetmobileapp.network.interactors.CCETRepositoryInteractor;

import javax.inject.Inject;

/**
 * Created by SAGAR MAHOBIA on 23-Jan-19. at 00:50
 */

@NoticeFragmentScope
public class NoticesViewModelFactory implements ViewModelProvider.Factory {
    private CCETRepositoryInteractor interactor;

    @Inject
    public NoticesViewModelFactory(CCETRepositoryInteractor interactor) {
        this.interactor = interactor;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(NoticesFragmentViewModel.class)) {
            return (T) new NoticesFragmentViewModel(interactor);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
