package com.sagar.ccetmobileapp.activities.host.account.login;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.sagar.ccetmobileapp.network.interactors.CCETRepositoryInteractor;
import com.sagar.ccetmobileapp.services.TokenService;
import com.sagar.ccetmobileapp.services.ValidatorService;

import javax.inject.Inject;

/**
 * Created by SAGAR MAHOBIA on 23-Jan-19. at 03:38
 */


@LoginFragmentScope
public class LoginFragmentViewModelFactory implements ViewModelProvider.Factory {

    private CCETRepositoryInteractor interactor;

    private ValidatorService validatorService;

    private TokenService tokenService;

    @Inject
    public LoginFragmentViewModelFactory(CCETRepositoryInteractor interactor, ValidatorService validatorService, TokenService tokenService) {
        this.interactor = interactor;
        this.validatorService = validatorService;
        this.tokenService = tokenService;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginFragmentViewModel.class)) {
            return (T) new LoginFragmentViewModel(interactor, validatorService, tokenService);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
