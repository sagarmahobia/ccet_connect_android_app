package com.sagar.ccetmobileapp.activities.host.account.signup;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModel;

import com.sagar.ccetmobileapp.network.interactors.CCETRepositoryInteractor;
import com.sagar.ccetmobileapp.network.models.serverentities.User;
import com.sagar.ccetmobileapp.responsemodel.Response;
import com.sagar.ccetmobileapp.services.ValidatorService;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by SAGAR MAHOBIA on 23-Jan-19. at 03:36
 */
class SignUpFragmentViewModel extends ViewModel {


    private final CCETRepositoryInteractor interactor;
    private final ValidatorService validatorService;

    private final CompositeDisposable disposable;

    private final MutableLiveData<Response> signUpResponse;

    SignUpFragmentViewModel(CCETRepositoryInteractor interactor,
                            ValidatorService validatorService) {
        this.interactor = interactor;
        this.validatorService = validatorService;

        disposable = new CompositeDisposable();

        signUpResponse = new MutableLiveData<>();
    }

    MutableLiveData<Response> getSignUpResponse() {
        return signUpResponse;
    }

    void onSingUp(String email,
                  String password,
                  String confirmPassword) {

        if (!validatorService.isValidEmail(email)
                || !validatorService.isValidPassword(password, confirmPassword)
        ) {
            //todo
            return;
        }

        User user = new User();
        user.setEmail(email);
        user.setPassWord(password);


        signUpResponse.setValue(Response.loading());
        disposable.add(interactor.signUp(user)
                .subscribe((otp) -> signUpResponse.setValue(Response.success(otp.getId())),
                        error -> signUpResponse.setValue(Response.error(error))
                ));

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        disposable.dispose();
    }
}
