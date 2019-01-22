package com.sagar.ccetmobileapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;

/**
 * Created by SAGAR MAHOBIA on 29-Oct-18. at 16:44
 */

@Module(includes = NetworkModule.class)
class ApplicationModule {

    @Provides
    @ApplicationScope
    Context provideContext(android.app.Application application) {
        return application;
    }

    @ApplicationScope
    @Provides
    SharedPreferences sharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @ApplicationScope
    @Provides
    Gson gson() {
        return new Gson();
    }
}
