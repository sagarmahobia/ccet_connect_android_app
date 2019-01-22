package com.sagar.ccetmobileapp.activities.home;

import dagger.Module;
import dagger.Provides;

/**
 * Created by SAGAR MAHOBIA on 01-Nov-18. at 16:10
 */

@Module
public class HomeActivityModule {

    @Provides
    @HomeActivityScope
    Contract.View view(HomeActivity homeActivity) {
        return homeActivity;
    }

    @Provides
    @HomeActivityScope
    Contract.Presenter presenter(Presenter presenter) {
        return presenter;
    }

}
