package com.sagar.ccetmobileapp.activities.host.account.otp;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModel;

import com.sagar.ccetmobileapp.network.interactors.CCETRepositoryInteractor;
import com.sagar.ccetmobileapp.network.models.serverentities.Otp;
import com.sagar.ccetmobileapp.responsemodel.Response;
import com.sagar.ccetmobileapp.services.TokenService;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by SAGAR MAHOBIA on 23-Jan-19. at 03:28
 */
class OtpFragmentViewModel extends ViewModel {

    private final CCETRepositoryInteractor interactor;
    private TokenService tokenService;

    private final CompositeDisposable disposable;

    private final MutableLiveData<Response> otpResponse;

    OtpFragmentViewModel(CCETRepositoryInteractor interactor,
                         TokenService tokenService) {

        this.interactor = interactor;
        this.tokenService = tokenService;

        disposable = new CompositeDisposable();

        otpResponse = new MutableLiveData<>();
    }

    MutableLiveData<Response> getOtpResponse() {
        return otpResponse;
    }

    void onSignUpOtpSubmit(String otp, int otpId) {
        try {
            Integer.parseInt(otp);
        } catch (NumberFormatException e) {
//            view.showMessage("Please enter valid OTP.");//todo
            return;
        }
        if (!(otp.length() == 4)) {
//            view.showMessage("Please enter valid OTP.");///todo
            return;
        }

        Otp otpBean = new Otp();
        otpBean.setOtp(otp);
        otpBean.setId(otpId);

        otpResponse.setValue(Response.loading());
        disposable.add(interactor.verifyOtp(otpBean).
                subscribe(authStatus -> {
                    tokenService.saveToken(authStatus.getToken());
                    otpResponse.setValue(Response.success(true));
                }, error -> otpResponse.setValue(Response.error(error))));

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        disposable.dispose();
    }
}
