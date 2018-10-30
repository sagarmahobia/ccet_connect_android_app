package com.sagar.ccetmobileapp.activities;

import android.arch.lifecycle.LifecycleObserver;

/**
 * Created by SAGAR MAHOBIA on 29-Oct-18. at 17:02
 */
public interface Contract {
    interface View {
        void askForOtp();

        void onSuccessSignIn();

    }

    interface Presenter extends LifecycleObserver {
        void onSingUp(String firstName, String lastName, String email, String password, String confirmPassword, String admissionYear, String admissionSem);

        void onSignIn(String loginEmail, String loginPassword);

        void onSignUpOtpSubmit(String otp);

    }
}
