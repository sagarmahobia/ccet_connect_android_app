package com.sagar.ccetmobileapp.activities.assignments;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;

import com.sagar.ccetmobileapp.network.interactors.CCETRepositoryInteractor;
import com.sagar.ccetmobileapp.network.models.assignments.Assignment;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by SAGAR MAHOBIA on 01-Nov-18. at 23:32
 */

@AssignmentsScope
public class Presenter implements Contract.Presenter {

    @Inject
    Contract.View view;

    @Inject
    CCETRepositoryInteractor interactor;

    private CompositeDisposable disposable;

    @Inject
    public Presenter(AssignmentsComponent component) {
        component.inject(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        disposable = new CompositeDisposable();
        load();
    }

    @Override
    public void load() {
        view.showProgress();
        disposable.add(interactor.getAssignments().subscribe(a -> {
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
