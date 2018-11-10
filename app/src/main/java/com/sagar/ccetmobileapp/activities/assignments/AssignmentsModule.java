package com.sagar.ccetmobileapp.activities.assignments;

import dagger.Module;
import dagger.Provides;

/**
 * Created by SAGAR MAHOBIA on 01-Nov-18. at 23:19
 */

@Module
public class AssignmentsModule {

    private AssignmentsActivity assignmentsActivity;

    AssignmentsModule(AssignmentsActivity assignmentsActivity) {
        this.assignmentsActivity = assignmentsActivity;
    }

    @Provides
    @AssignmentsScope
    AssignmentsActivity assignmentsActivity() {
        return assignmentsActivity;
    }

    @Provides
    @AssignmentsScope
    Contract.View view(AssignmentsActivity assignmentsActivity) {
        return assignmentsActivity;
    }

    @Provides
    @AssignmentsScope
    Contract.Presenter presenter(Presenter presenter) {
        return presenter;
    }


}
