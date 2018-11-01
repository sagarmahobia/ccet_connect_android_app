package com.sagar.ccetmobileapp.activities.home;

import com.sagar.ccetmobileapp.ApplicationComponent;

import dagger.Component;

/**
 * Created by SAGAR MAHOBIA on 01-Nov-18. at 16:11
 */
@HomeActivityScope
@Component(modules = HomeActivityModule.class, dependencies = ApplicationComponent.class)
public interface HomeActivityComponent {

    void inject(HomeActivity homeActivity);

    void inject(Presenter presenter);
}
