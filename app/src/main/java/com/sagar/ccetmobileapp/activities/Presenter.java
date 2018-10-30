package com.sagar.ccetmobileapp.activities;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.SharedPreferences;

import com.sagar.ccetmobileapp.network.interactors.CCETRepositoryInteractor;
import com.sagar.ccetmobileapp.network.models.OtpModel;
import com.sagar.ccetmobileapp.network.models.SignInModel;
import com.sagar.ccetmobileapp.network.models.SignUpModel;
import com.sagar.ccetmobileapp.services.ValidatorService;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

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


        int admissionSemester = 0;

        try {
            admissionSemester = Integer.parseInt(admissionSem);
            Integer.parseInt(admissionSem);
        } catch (NumberFormatException e) {
            return;//todo
        }


        if (firstName.isEmpty()
                || lastName.isEmpty()
                || !validatorService.isValidEmail(email)
                || !validatorService.isValidPassword(password, confirmPassword)
                || !(admissionYear.length() == 4)
                || !(admissionSemester >= 1 && admissionSemester <= 8)
                ) {
            return;//todo
        }
        signUpEmail = email;
        SignUpModel signUpModel = new SignUpModel();
        signUpModel.setFirstName(firstName);
        signUpModel.setLastName(lastName);
        signUpModel.setEmail(email);
        signUpModel.setPassWord(password);
        signUpModel.setAdmissionYear(admissionYear);
        signUpModel.setAdmissionSemester(admissionSemester);

        disposable.add(interactor.signUp(signUpModel)
                .subscribe((status) -> {
                    if (status.getStatus().equalsIgnoreCase("success")) {
                        view.askForOtp();
                        //todo
                    }
                }));

    }

    @Override
    public void onSignIn(String email, String password) {

        if (!validatorService.isValidEmail(email) || !validatorService.isValidPassword(password)) {
            return;   // /todo
        }
        SignInModel signInModel = new SignInModel();
        signInModel.setEmail(email);
        signInModel.setPassword(password);
        disposable.add(interactor.signIn(signInModel)
                .subscribe(authStatus -> {
                    if (authStatus.isAuthenticated()) {
                        String token = authStatus.getToken();
                        saveToken(token);
                        view.onSuccessSignIn();
                        //todo
                    }
                }));
    }

    @Override
    public void onSignUpOtpSubmit(String otp) {
        try {
            Integer.parseInt(otp);
        } catch (NumberFormatException e) {
            return;//todo
        }
        if (!(otp.length() == 4)) {
            return;//todo
        }

        OtpModel otpModel = new OtpModel();
        otpModel.setOtp(otp);
        otpModel.setEmail(signUpEmail);
        disposable.add(interactor.verifyOtp(otpModel).
                subscribe(authStatus -> {
                    if (authStatus.isAuthenticated()) {
                        saveToken(authStatus.getToken());
                        view.onSuccessSignIn();
                    } else {
                        // TODO
                    }
                }));

    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        disposable.dispose();
    }

    private void saveToken(String token) {
        sharedPreferences.edit().putString("token", token).apply();
    }
}
