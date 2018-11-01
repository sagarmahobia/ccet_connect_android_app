package com.sagar.ccetmobileapp.activities.account;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.SharedPreferences;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.sagar.ccetmobileapp.network.errors.ErrorCode;
import com.sagar.ccetmobileapp.network.interactors.CCETRepositoryInteractor;
import com.sagar.ccetmobileapp.network.models.Error;
import com.sagar.ccetmobileapp.network.models.OtpModel;
import com.sagar.ccetmobileapp.network.models.SignInModel;
import com.sagar.ccetmobileapp.network.models.SignUpModel;
import com.sagar.ccetmobileapp.services.ErrorConverterService;
import com.sagar.ccetmobileapp.services.ValidatorService;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.ResponseBody;

/**
 * Created by SAGAR MAHOBIA on 29-Oct-18. at 17:03
 */

@AccountingActivityScope
class Presenter implements Contract.Presenter {

    @Inject
    Contract.View view;

    @Inject
    CCETRepositoryInteractor interactor;

    @Inject
    ValidatorService validatorService;

    @Inject
    ErrorConverterService errorConverterService;

    private CompositeDisposable disposable;

    @Inject
    SharedPreferences sharedPreferences;

    private String signUpEmail;

    @Inject
    Presenter(AccountingActivityComponent component) {
        component.inject(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        disposable = new CompositeDisposable();
    }

    @Override
    public void onSingUp(String firstName,
                         String lastName,
                         String email,
                         String password,
                         String confirmPassword,
                         String admissionYear,
                         String admissionSem) {


        int admissionSemester;

        try {
            admissionSemester = Integer.parseInt(admissionSem);
            Integer.parseInt(admissionSem);
        } catch (NumberFormatException e) {
            view.showMessage("Please select valid admission year and semester.");
            return;
        }


        if (firstName.isEmpty()
                || lastName.isEmpty()
                || !validatorService.isValidEmail(email)
                || !validatorService.isValidPassword(password, confirmPassword)
                || !(admissionYear.length() == 4)
                || !(admissionSemester >= 1 && admissionSemester <= 8)
                ) {
            view.showMessage("Please enter valid information.");
            return;
        }
        signUpEmail = email;
        SignUpModel signUpModel = new SignUpModel();
        signUpModel.setFirstName(firstName);
        signUpModel.setLastName(lastName);
        signUpModel.setEmail(email);
        signUpModel.setPassWord(password);
        signUpModel.setAdmissionYear(admissionYear);
        signUpModel.setAdmissionSemester(admissionSemester);


        view.showProgress();
        disposable.add(interactor.signUp(signUpModel)
                .subscribe((status) -> {
                    view.hideProgress();
                    view.showSignUpOtpScreen();

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

                            view.showMessage(e.getErrorCode() + " " + e.getMessage());
                            //todo
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
        SignInModel signInModel = new SignInModel();
        signInModel.setEmail(email);
        signInModel.setPassword(password);

        view.showProgress();
        disposable.add(interactor.signIn(signInModel)
                .subscribe(authStatus -> {
                    view.hideProgress();
                        String token = authStatus.getToken();
                        saveToken(token);
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

        OtpModel otpModel = new OtpModel();
        otpModel.setOtp(otp);
        otpModel.setEmail(signUpEmail);

        view.showProgress();
        disposable.add(interactor.verifyOtp(otpModel).
                subscribe(authStatus -> {
                    view.hideProgress();
                        saveToken(authStatus.getToken());
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


    private void saveToken(String token) {
        sharedPreferences.edit().putString("token", token).apply();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        disposable.dispose();
    }
}
