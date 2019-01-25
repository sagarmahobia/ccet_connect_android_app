package com.sagar.ccetmobileapp.activities.host.syllabus;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.sagar.ccetmobileapp.network.interactors.CCETRepositoryInteractor;
import com.sagar.ccetmobileapp.network.models.serverentities.Syllabus;
import com.sagar.ccetmobileapp.responsemodel.Response;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by SAGAR MAHOBIA on 25-Jan-19. at 20:36
 */
public class SyllabusFragmentViewModel extends ViewModel {

    private CCETRepositoryInteractor interactor;

    private MutableLiveData<Response> listResponse;

    private CompositeDisposable disposable;


    SyllabusFragmentViewModel(CCETRepositoryInteractor interactor) {
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

        Syllabus syllabus = new Syllabus();
        syllabus.setSemester(semester);
        syllabus.setBranchId(branchId);
        listResponse.setValue(Response.loading());
        disposable.add(interactor.getSyllabuses(syllabus).
                subscribe(
                        a -> {
                            List<Syllabus> syllabuses = a.getSyllabuses();
                            if (syllabuses.isEmpty()) {
                                listResponse.setValue(Response.success(null));
                            } else {
                                listResponse.setValue(Response.success(syllabuses.get(0)));
                            }
                        },
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
