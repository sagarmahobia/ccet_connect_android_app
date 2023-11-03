package com.sagar.ccetmobileapp;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;


/**
 * Created by SAGAR MAHOBIA on 29-Oct-18. at 16:46
 */

@ApplicationScope
@Component(modules = {AndroidInjectionModule.class, ApplicationModule.class,ActivityProvider.class})
public interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(android.app.Application application);

        ApplicationComponent build();
    }

    void inject(Application application);

}
