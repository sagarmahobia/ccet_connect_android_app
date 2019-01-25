package com.sagar.ccetmobileapp.activities.host.account.otp;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.sagar.ccetmobileapp.network.interactors.CCETRepositoryInteractor;
import com.sagar.ccetmobileapp.services.TokenService;

import javax.inject.Inject;

/**
 * Created by SAGAR MAHOBIA on 23-Jan-19. at 03:38
 */

@OtpFragmentScope
public class OtpFragmentViewModelFactory implements ViewModelProvider.Factory {

    private CCETRepositoryInteractor interactor;

    private TokenService tokenService;

    @Inject
    OtpFragmentViewModelFactory(CCETRepositoryInteractor interactor, TokenService tokenService) {
        this.interactor = interactor;
        this.tokenService = tokenService;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(OtpFragmentViewModel.class)) {
            return (T) new OtpFragmentViewModel(interactor, tokenService);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}