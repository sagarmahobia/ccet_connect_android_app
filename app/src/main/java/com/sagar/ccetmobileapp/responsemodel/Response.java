package com.sagar.ccetmobileapp.responsemodel;

import android.support.annotation.NonNull;

import static com.sagar.ccetmobileapp.responsemodel.Status.ERROR;
import static com.sagar.ccetmobileapp.responsemodel.Status.LOADING;
import static com.sagar.ccetmobileapp.responsemodel.Status.SUCCESS;


/**
 * Response holder provided to the UI
 */
public class Response<T> {

    private final Status status;

    private final T data;

    private final Throwable error;

    private Response(Status status, T data, Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public T getData() {
        return data;
    }

    @NonNull
    public Status getStatus() {
        return status;
    }

    public Throwable getError() {
        return error;
    }

    @NonNull
    public static <T> Response loading() {
        return new Response<T>(LOADING, null, null);
    }

    @NonNull
    public static <T> Response success(T data) {
        return new Response<T>(SUCCESS, data, null);
    }

    @NonNull
    public static <T> Response error(@NonNull Throwable error) {
        return new Response<T>(ERROR, null, error);
    }

}
