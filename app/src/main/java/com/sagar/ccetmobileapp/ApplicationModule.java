package com.sagar.ccetmobileapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by SAGAR MAHOBIA on 29-Oct-18. at 16:44
 */

@Module
public class ApplicationModule {

    private Application application;

    ApplicationModule(Application application) {
        this.application = application;
    }

    @ApplicationScope
    @Provides
    public Application getApplication() {
        return application;
    }

    @ApplicationScope
    @Provides
    public Context getContext() {
        return application.getApplicationContext();
    }

    @ApplicationScope
    @Provides
    SharedPreferences sharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }


}
