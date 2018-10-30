package com.sagar.ccetmobileapp;

import android.content.SharedPreferences;

import com.sagar.ccetmobileapp.network.interactors.CCETRepositoryInteractor;
import com.sagar.ccetmobileapp.services.ValidatorService;

import dagger.Component;


/**
 * Created by SAGAR MAHOBIA on 29-Oct-18. at 16:46
 */

@ApplicationScope
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface ApplicationComponent {

    void inject(Application application);

    SharedPreferences sharedPreferences();

    CCETRepositoryInteractor ccetRepositoryInteractor();

    ValidatorService validatorService();
}
