package com.sagar.ccetmobileapp;

import com.sagar.ccetmobileapp.activities.host.FragmentProvider;
import com.sagar.ccetmobileapp.activities.host.HostActivity;
import com.sagar.ccetmobileapp.activities.host.HostActivityModule;
import com.sagar.ccetmobileapp.activities.host.HostActivityScope;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by SAGAR MAHOBIA on 22-Jan-19. at 20:08
 */
@Module
abstract class ActivityProvider {

    @ContributesAndroidInjector(modules = {HostActivityModule.class, FragmentProvider.class})
    @HostActivityScope
    abstract HostActivity hostActivity();

}
