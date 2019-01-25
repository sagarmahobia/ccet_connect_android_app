package com.sagar.ccetmobileapp.activities.host.assignments;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.sagar.ccetmobileapp.network.interactors.CCETRepositoryInteractor;
import com.sagar.ccetmobileapp.network.models.serverentities.Assignment;
import com.sagar.ccetmobileapp.responsemodel.Response;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by SAGAR MAHOBIA on 01-Nov-18. at 23:32
 */

public class AssignmentsFragmentViewModel extends ViewModel {

    private CCETRepositoryInteractor interactor;

    private MutableLiveData<Response> listResponse;

    private CompositeDisposable disposable;


    AssignmentsFragmentViewModel(CCETRepositoryInteractor interactor) {
        this.interactor = interactor;
        listResponse = new MutableLiveData<>();
        disposable = new CompositeDisposable();
    }

    MutableLiveData<Response> getListResponse() {
        return listResponse;
    }

    void load(int branchId, int semester) {

        if (branchId == 0 || semester == 0) {
            return;
        }

        Assignment assignment = new Assignment();
        assignment.setSemester(semester);
        assignment.setBranchId(branchId);
        listResponse.setValue(Response.loading());
        disposable.add(interactor.getAssignments(assignment).
                subscribe(
                        a -> listResponse.setValue(Response.success(a.getAssignments())),
                        error -> listResponse.setValue(Response.error(error))
                )
        );

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
