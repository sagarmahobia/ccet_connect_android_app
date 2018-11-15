package com.sagar.ccetmobileapp.activities.notices;

import com.sagar.ccetmobileapp.ApplicationComponent;

import dagger.Component;

/**
 * Created by SAGAR MAHOBIA on 14-Nov-18. at 12:11
 */
@NoticeActivityScope
@Component(modules = NoticesActivityModule.class, dependencies = ApplicationComponent.class)
public interface NoticesActivityComponent {

    void inject(NoticesActivity noticesActivity);

    void inject(Presenter presenter);
}
