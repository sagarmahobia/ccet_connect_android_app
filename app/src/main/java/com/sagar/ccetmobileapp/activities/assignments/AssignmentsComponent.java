package com.sagar.ccetmobileapp.activities.assignments;

import com.sagar.ccetmobileapp.ApplicationComponent;

import dagger.Component;

/**
 * Created by SAGAR MAHOBIA on 01-Nov-18. at 23:20
 */
@AssignmentsScope
@Component(modules = AssignmentsModule.class, dependencies = ApplicationComponent.class)
public interface AssignmentsComponent {
    void inject(AssignmentsActivity assignmentsActivity);
    void inject(Presenter presenter);
}
