package com.sagar.ccetmobileapp.activities;

import dagger.Module;
import dagger.Provides;

/**
 * Created by SAGAR MAHOBIA on 29-Oct-18. at 16:55
 */

@Module
public class AccountingActivityModule {

    private AccountingActivity accountingActivity;


    AccountingActivityModule(AccountingActivity accountingActivity) {
        this.accountingActivity = accountingActivity;
    }

    @AccountingActivityScope
    @Provides
    AccountingActivity getAccountingActivity() {
        return accountingActivity;
    }

    @AccountingActivityScope
    @Provides
    Contract.View view(AccountingActivity accountingActivity) {
        return accountingActivity;
    }

    @AccountingActivityScope
    @Provides
    Contract.Presenter presenter(Presenter presenter) {
        return presenter;
    }
}
