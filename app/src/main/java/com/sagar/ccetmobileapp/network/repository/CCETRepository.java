package com.sagar.ccetmobileapp.network.repository;

import com.sagar.ccetmobileapp.network.models.AuthStatus;
import com.sagar.ccetmobileapp.network.models.OtpModel;
import com.sagar.ccetmobileapp.network.models.SignInModel;
import com.sagar.ccetmobileapp.network.models.SignUpModel;
import com.sagar.ccetmobileapp.network.models.Status;
import com.sagar.ccetmobileapp.network.models.assignments.Assignments;
import com.sagar.ccetmobileapp.network.models.notices.Notices;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by SAGAR MAHOBIA on 29-Oct-18. at 17:20
 */
public interface CCETRepository {

    @POST("api/v1/public/user/sign_up")
    Single<Status> signUp(@Body SignUpModel signUpModel);

    @POST("api/v1/public/user/sign_in")
    Single<AuthStatus> signIn(@Body SignInModel signInModel);

    @POST("api/v1/public/user/verify_otp")
    Single<AuthStatus> verifyOtp(@Body OtpModel otpModel);

    @GET("/api/v1/protected/assignments")
    Single<Assignments> getAssignments();

    @GET("api/v1/protected/notices")
    Single<Notices> getNoticesSingle();
}
