package com.sagar.ccetmobileapp;

import com.sagar.ccetmobileapp.network.interactors.CCETRepositoryInteractor;
import com.sagar.ccetmobileapp.services.ErrorConverterService;
import com.sagar.ccetmobileapp.services.TokenService;
import com.sagar.ccetmobileapp.services.ValidatorService;

import dagger.Component;


/**
 * Created by SAGAR MAHOBIA on 29-Oct-18. at 16:46
 */

@ApplicationScope
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface ApplicationComponent {

    void inject(Application application);

    TokenService tokenService();

    CCETRepositoryInteractor ccetRepositoryInteractor();

    ValidatorService validatorService();

    ErrorConverterService errorConverterService();
}
