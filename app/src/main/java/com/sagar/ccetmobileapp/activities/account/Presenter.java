package com.sagar.ccetmobileapp.activities.account;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.sagar.ccetmobileapp.network.errors.ErrorCode;
import com.sagar.ccetmobileapp.network.interactors.CCETRepositoryInteractor;
import com.sagar.ccetmobileapp.network.models.Error;
import com.sagar.ccetmobileapp.network.models.serverentities.Otp;
import com.sagar.ccetmobileapp.network.models.serverentities.User;
import com.sagar.ccetmobileapp.services.ErrorConverterService;
import com.sagar.ccetmobileapp.services.TokenService;
import com.sagar.ccetmobileapp.services.ValidatorService;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.ResponseBody;

/**
 * Created by SAGAR MAHOBIA on 29-Oct-18. at 17:03
 */

@AccountingActivityScope
class Presenter implements Contract.Presenter {
    private Contract.View view;

    private CCETRepositoryInteractor interactor;

    private ValidatorService validatorService;

    private ErrorConverterService errorConverterService;

    private CompositeDisposable disposable;

    @Inject
    TokenService tokenService;

    private int otpId;

    @Inject
    Presenter(Contract.View view, CCETRepositoryInteractor interactor, ValidatorService validatorService, ErrorConverterService errorConverterService) {
        this.view = view;
        this.interactor = interactor;
        this.validatorService = validatorService;
        this.errorConverterService = errorConverterService;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        disposable = new CompositeDisposable();
    }

    @Override
    public void onSingUp(String email,
                         String password,
                         String confirmPassword) {

        if (!validatorService.isValidEmail(email)
                || !validatorService.isValidPassword(password, confirmPassword)
                ) {
            view.showMessage("Please enter valid information.");
            return;
        }

        User user = new User();
        user.setEmail(email);
        user.setPassWord(password);


        view.showProgress();
        disposable.add(interactor.signUp(user)
                .subscribe((otp) -> {
                    view.hideProgress();
                    view.showSignUpOtpScreen();
                    otpId = otp.getId();
                }, error -> {
                    view.hideProgress();
                    if (error instanceof HttpException) {
                        ResponseBody responseBody = ((HttpException) error).response().errorBody();
                        if (responseBody != null) {
                            Error e = errorConverterService.getError(responseBody.string());
                            view.showSignUpScreen();
                            ErrorCode errorCode = ErrorCode.getErrorCode(e.getErrorCode());
                            switch (errorCode) {
                                case InvalidInputException:
                                    view.showMessage("Please enter valid info");
                                    break;
                                case EmailAlreadyUsedException:
                                    view.showMessage("Email already is use. Please provide a different email");
                                    break;
                                case NULL:
                                default:
                                    view.showMessage("Something went wrong.");
                            }
                         }
                    }
                }));

    }

    @Override
    public void onSignIn(String email, String password) {

        if (!validatorService.isValidEmail(email) || !validatorService.isValidPassword(password)) {
            view.showMessage("Please enter valid email and password.");
            return;
        }
        User user = new User();
        user.setEmail(email);
        user.setPassWord(password);

        view.showProgress();
        disposable.add(interactor.signIn(user)
                .subscribe(authStatus -> {
                    view.hideProgress();
                        String token = authStatus.getToken();
                    tokenService.saveToken(token);
                        view.onSuccessSignIn();
                }, error -> {
                    view.hideProgress();
                    if (error instanceof HttpException) {
                        ResponseBody responseBody = ((HttpException) error).response().errorBody();
                        if (responseBody != null) {
                            Error e = errorConverterService.getError(responseBody.string());
                            view.showSignInScreen();
                            ErrorCode errorCode = ErrorCode.getErrorCode(e.getErrorCode());
                            switch (errorCode) {
                                case InvalidInputException:
                                    view.showMessage("Please enter valid info");
                                    break;
                                case UserNotFoundException:
                                    view.showMessage("User not found");
                                    break;
                                default:
                                    view.showMessage("Something went wrong.");
                            }
                        }
                    }
                }));
    }

    @Override
    public void onSignUpOtpSubmit(String otp) {
        try {
            Integer.parseInt(otp);
        } catch (NumberFormatException e) {
            view.showMessage("Please enter valid OTP.");
            return;
        }
        if (!(otp.length() == 4)) {
            view.showMessage("Please enter valid OTP.");
            return;
        }

        Otp otpBean = new Otp();
        otpBean.setOtp(otp);
        otpBean.setId(otpId);

        view.showProgress();
        disposable.add(interactor.verifyOtp(otpBean).
                subscribe(authStatus -> {
                    view.hideProgress();
                    tokenService.saveToken(authStatus.getToken());
                    view.onSuccessSignIn();
                }, error -> {
                    view.hideProgress();
                    if (error instanceof HttpException) {
                        ResponseBody responseBody = ((HttpException) error).response().errorBody();
                        if (responseBody != null) {
                            Error e = errorConverterService.getError(responseBody.string());
                            view.showSignUpOtpScreen();
                            ErrorCode errorCode = ErrorCode.getErrorCode(e.getErrorCode());
                            switch (errorCode) {
                                case InvalidInputException:
                                    view.showMessage("Please enter valid info");
                                    break;
                                case InvalidCredentialsException:
                                    view.showMessage("Invalid OTP.");
                                    break;
                                default:
                                    view.showMessage("Something went wrong.");
                            }
                        }
                    }
                }));

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        disposable.dispose();
    }
}
