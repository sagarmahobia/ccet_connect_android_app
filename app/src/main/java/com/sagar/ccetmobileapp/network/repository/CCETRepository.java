package com.sagar.ccetmobileapp.network.repository;

import com.sagar.ccetmobileapp.network.models.Assignments;
import com.sagar.ccetmobileapp.network.models.AuthStatus;
import com.sagar.ccetmobileapp.network.models.Notices;
import com.sagar.ccetmobileapp.network.models.serverentities.Assignment;
import com.sagar.ccetmobileapp.network.models.serverentities.Otp;
import com.sagar.ccetmobileapp.network.models.serverentities.User;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by SAGAR MAHOBIA on 29-Oct-18. at 17:20
 */
public interface CCETRepository {

    @POST("api/v1/public/user/sign_up")
    Single<Otp> signUp(@Body User user);

    @POST("api/v1/public/user/verify_otp")
    Single<AuthStatus> verifyOtp(@Body Otp otp);

    @POST("api/v1/public/user/sign_in")
    Single<AuthStatus> signIn(@Body User user);

    @POST("api/v1/public/assignments")
    Single<Assignments> getAssignments(@Body Assignment assignment);

    @GET("api/v1/protected/notices")
    Single<Notices> getNoticesSingle();
}
