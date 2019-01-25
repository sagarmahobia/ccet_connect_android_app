package com.sagar.ccetmobileapp.activities.host.syllabus;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.sagar.ccetmobileapp.network.interactors.CCETRepositoryInteractor;

import javax.inject.Inject;

/**
 * Created by SAGAR MAHOBIA on 25-Jan-19. at 20:39
 */

@SyllabusFragmentScope
public class SyllabusFragmentViewModelFactory implements ViewModelProvider.Factory {

    private CCETRepositoryInteractor interactor;

    @Inject
    SyllabusFragmentViewModelFactory(CCETRepositoryInteractor interactor) {
        this.interactor = interactor;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SyllabusFragmentViewModel.class)) {
            return (T) new SyllabusFragmentViewModel(interactor);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
