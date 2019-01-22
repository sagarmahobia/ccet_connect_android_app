package com.sagar.ccetmobileapp.activities.notices;

import dagger.Module;
import dagger.Provides;

/**
 * Created by SAGAR MAHOBIA on 14-Nov-18. at 12:07
 */

@Module
public class NoticesActivityModule {

    @Provides
    @NoticeActivityScope
    Contract.View view(NoticesActivity noticesActivity) {
        return noticesActivity;
    }

    @Provides
    @NoticeActivityScope
    Contract.Presenter presenter(Presenter presenter) {
        return presenter;
    }

}
