package com.sagar.ccetmobileapp.services;

import com.google.gson.Gson;
import com.sagar.ccetmobileapp.ApplicationScope;
import com.sagar.ccetmobileapp.network.models.Error;

import javax.inject.Inject;

/**
 * Created by SAGAR MAHOBIA on 31-Oct-18. at 16:51
 */
@ApplicationScope
public class ErrorConverterService {

    private Gson gson;

    @Inject
    ErrorConverterService(Gson gson) {
        this.gson = gson;
    }

    public Error getError(String json) {
        return gson.fromJson(json, Error.class);
    }
}
