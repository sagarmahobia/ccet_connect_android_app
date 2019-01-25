package com.sagar.ccetmobileapp.activities.host.account.login;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.sagar.ccetmobileapp.network.interactors.CCETRepositoryInteractor;
import com.sagar.ccetmobileapp.network.models.serverentities.User;
import com.sagar.ccetmobileapp.responsemodel.Response;
import com.sagar.ccetmobileapp.services.TokenService;
import com.sagar.ccetmobileapp.services.ValidatorService;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by SAGAR MAHOBIA on 23-Jan-19. at 03:26
 */
class LoginFragmentViewModel extends ViewModel {

    private final CCETRepositoryInteractor interactor;
    private final ValidatorService validatorService;
    private final TokenService tokenService;

    private final MutableLiveData<Response> loginResponse;

    private final CompositeDisposable disposable;

    LoginFragmentViewModel(CCETRepositoryInteractor interactor, ValidatorService validatorService, TokenService tokenService) {
        this.interactor = interactor;
        this.validatorService = validatorService;
        this.tokenService = tokenService;

        loginResponse = new MutableLiveData<>();

        disposable = new CompositeDisposable();

    }

    MutableLiveData<Response> getLoginResponse() {
        return loginResponse;
    }

    void onSignIn(String email, String password) {

        if (!validatorService.isValidEmail(email) || !validatorService.isValidPassword(password)) {
            //todo
            return;
        }
        User user = new User();
        user.setEmail(email);
        user.setPassWord(password);
        loginResponse.setValue(Response.loading());
        disposable.add(
                interactor.signIn(user).
                        subscribe(authStatus -> {
                            String token = authStatus.getToken();
                            tokenService.saveToken(token);
                            loginResponse.setValue(Response.success(true));
                        }, error -> loginResponse.setValue(Response.error(error)))
        );
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
