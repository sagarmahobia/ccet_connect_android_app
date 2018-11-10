package com.sagar.ccetmobileapp.network.interactors;

import com.sagar.ccetmobileapp.ApplicationScope;
import com.sagar.ccetmobileapp.network.models.AuthStatus;
import com.sagar.ccetmobileapp.network.models.OtpModel;
import com.sagar.ccetmobileapp.network.models.SignInModel;
import com.sagar.ccetmobileapp.network.models.SignUpModel;
import com.sagar.ccetmobileapp.network.models.Status;
import com.sagar.ccetmobileapp.network.models.assignments.Assignments;
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
    public Single<Status> signUp(SignUpModel signUpModel) {

        return repository.signUp(signUpModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    @Override
    public Single<AuthStatus> signIn(SignInModel signInModel) {
        return repository.signIn(signInModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<AuthStatus> verifyOtp(OtpModel otpModel) {
        return repository.verifyOtp(otpModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<Assignments> getAssignments() {
        return repository.getAssignments()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
