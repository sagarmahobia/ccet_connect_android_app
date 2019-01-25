package com.sagar.ccetmobileapp.network.interactors;

import com.sagar.ccetmobileapp.ApplicationScope;
import com.sagar.ccetmobileapp.network.models.Assignments;
import com.sagar.ccetmobileapp.network.models.AuthStatus;
import com.sagar.ccetmobileapp.network.models.Notices;
import com.sagar.ccetmobileapp.network.models.Syllabuses;
import com.sagar.ccetmobileapp.network.models.serverentities.Assignment;
import com.sagar.ccetmobileapp.network.models.serverentities.Otp;
import com.sagar.ccetmobileapp.network.models.serverentities.Syllabus;
import com.sagar.ccetmobileapp.network.models.serverentities.User;
import com.sagar.ccetmobileapp.network.repository.CCETRepository;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by SAGAR MAHOBIA on 29-Oct-18. at 17:50
 */

@ApplicationScope
public class CCETRepositoryInteractor implements CCETRepository {


    private CCETRepository repository;

    @Inject
    CCETRepositoryInteractor(CCETRepository repository) {
        this.repository = repository;
    }

    @Override
    public Single<Otp> signUp(User user) {

        return repository.signUp(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    @Override
    public Single<AuthStatus> signIn(User user) {
        return repository.signIn(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<AuthStatus> verifyOtp(Otp otp) {
        return repository.verifyOtp(otp)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<Assignments> getAssignments(Assignment assignment) {
        return repository.getAssignments(assignment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<Notices> getNoticesSingle() {
        return repository.getNoticesSingle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<Syllabuses> getSyllabuses(Syllabus syllabus) {
        return repository.getSyllabuses(syllabus)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
