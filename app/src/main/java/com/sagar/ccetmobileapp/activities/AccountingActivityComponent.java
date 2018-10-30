package com.sagar.ccetmobileapp.activities;

import com.sagar.ccetmobileapp.ApplicationComponent;

import dagger.Component;

/**
 * Created by SAGAR MAHOBIA on 29-Oct-18. at 16:56
 */

@AccountingActivityScope
@Component(modules = AccountingActivityModule.class, dependencies = ApplicationComponent.class)
public interface AccountingActivityComponent {

    void inject(AccountingActivity accountingActivity);

    void inject(Presenter presenter);
}
