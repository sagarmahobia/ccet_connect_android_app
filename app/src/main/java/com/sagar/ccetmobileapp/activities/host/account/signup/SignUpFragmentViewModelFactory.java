package com.sagar.ccetmobileapp.activities.host.account.signup;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.sagar.ccetmobileapp.network.interactors.CCETRepositoryInteractor;
import com.sagar.ccetmobileapp.services.ValidatorService;

import javax.inject.Inject;

/**
 * Created by SAGAR MAHOBIA on 23-Jan-19. at 03:37
 */

@SignUpFragmentScope
public class SignUpFragmentViewModelFactory implements ViewModelProvider.Factory {

    private CCETRepositoryInteractor interactor;

    private ValidatorService validatorService;

    @Inject
    SignUpFragmentViewModelFactory(CCETRepositoryInteractor interactor, ValidatorService validatorService) {
        this.interactor = interactor;
        this.validatorService = validatorService;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SignUpFragmentViewModel.class)) {
            return (T) new SignUpFragmentViewModel(interactor, validatorService);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
