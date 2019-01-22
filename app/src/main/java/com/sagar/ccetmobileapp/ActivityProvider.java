package com.sagar.ccetmobileapp;

import com.sagar.ccetmobileapp.activities.account.AccountingActivity;
import com.sagar.ccetmobileapp.activities.account.AccountingActivityModule;
import com.sagar.ccetmobileapp.activities.account.AccountingActivityScope;
import com.sagar.ccetmobileapp.activities.assignments.AssignmentsActivity;
import com.sagar.ccetmobileapp.activities.assignments.AssignmentsActivityModule;
import com.sagar.ccetmobileapp.activities.assignments.AssignmentsScope;
import com.sagar.ccetmobileapp.activities.home.HomeActivity;
import com.sagar.ccetmobileapp.activities.home.HomeActivityModule;
import com.sagar.ccetmobileapp.activities.home.HomeActivityScope;
import com.sagar.ccetmobileapp.activities.notices.NoticeActivityScope;
import com.sagar.ccetmobileapp.activities.notices.NoticesActivity;
import com.sagar.ccetmobileapp.activities.notices.NoticesActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by SAGAR MAHOBIA on 22-Jan-19. at 20:08
 */
@Module
abstract class ActivityProvider {

    @ContributesAndroidInjector(modules = HomeActivityModule.class)
    @HomeActivityScope
    abstract HomeActivity homeActivity();

    @ContributesAndroidInjector(modules = AssignmentsActivityModule.class)
    @AssignmentsScope
    abstract AssignmentsActivity assignmentAssignment();

    @ContributesAndroidInjector(modules = AccountingActivityModule.class)
    @AccountingActivityScope
    abstract AccountingActivity accountingActivity();

    @ContributesAndroidInjector(modules = NoticesActivityModule.class)
    @NoticeActivityScope
    abstract NoticesActivity noticesActivity();

}
