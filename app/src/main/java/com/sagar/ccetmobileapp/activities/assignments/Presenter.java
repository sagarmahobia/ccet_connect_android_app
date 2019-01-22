package com.sagar.ccetmobileapp.activities.assignments;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;

import com.sagar.ccetmobileapp.network.interactors.CCETRepositoryInteractor;
import com.sagar.ccetmobileapp.network.models.serverentities.Assignment;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by SAGAR MAHOBIA on 01-Nov-18. at 23:32
 */

@AssignmentsScope
public class Presenter implements Contract.Presenter {
    private Contract.View view;

    private CCETRepositoryInteractor interactor;

    private CompositeDisposable disposable;

    @Inject
    public Presenter(Contract.View view, CCETRepositoryInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        disposable = new CompositeDisposable();
    }

    @Override
    public void load(int branchId, int semester) {

        if (branchId == 0 || semester == 0) {
            return;
        }

        Assignment assignment = new Assignment();
        assignment.setSemester(semester);
        assignment.setBranchId(branchId);
        view.showProgress();
        disposable.add(interactor.getAssignments(assignment).subscribe(a -> {
            List<Assignment> assignments = a.getAssignments();
            if (!assignments.isEmpty()) {
                view.hideProgress();
                view.hideMessage();
                view.showAssignments(assignments);
            } else {
                view.hideProgress();
                view.hideAssignments();
                view.showMessage("No assignments available");
            }

        }, error -> {
            view.hideProgress();
            view.hideAssignments();
            view.showMessage("Something went wrong.");
        }));

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        disposable = new CompositeDisposable();
    }


}
