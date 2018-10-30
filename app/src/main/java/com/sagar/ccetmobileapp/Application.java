package com.sagar.ccetmobileapp;

import android.app.Activity;

import javax.inject.Inject;

/**
 * Created by SAGAR MAHOBIA on 29-Oct-18. at 16:39
 */
public class Application extends android.app.Application {

    @Inject
    ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build()
                .inject(this);
    }


    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    public static Application getApplication(Activity activity) {
        return (Application) activity.getApplication();
    }
}
