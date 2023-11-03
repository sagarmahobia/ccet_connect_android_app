package com.sagar.ccetmobileapp.activities.host.assignments;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.sagar.ccetmobileapp.network.interactors.CCETRepositoryInteractor;

import javax.inject.Inject;

/**
 * Created by SAGAR MAHOBIA on 22-Jan-19. at 23:25
 */


@AssignmentsFragmentScope
public class AssignmentsFragmentViewModelFactory implements ViewModelProvider.Factory {
    private CCETRepositoryInteractor interactor;

    @Inject
    AssignmentsFragmentViewModelFactory(CCETRepositoryInteractor interactor) {
        this.interactor = interactor;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AssignmentsFragmentViewModel.class)) {
            return (T) new AssignmentsFragmentViewModel(interactor);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
