package com.sagar.ccetmobileapp.activities.assignments;

import android.arch.lifecycle.LifecycleObserver;

import com.sagar.ccetmobileapp.network.models.assignments.Assignment;

import java.util.List;

/**
 * Created by SAGAR MAHOBIA on 01-Nov-18. at 23:32
 */
public class Contract {
    interface View {
        void showAssignments(List<Assignment> assignments);

        void hideAssignments();

        void showMessage(String message);

        void hideMessage();

        void hideProgress();

        void showProgress();
    }

    interface Presenter extends LifecycleObserver {
        void load();

    }
}
