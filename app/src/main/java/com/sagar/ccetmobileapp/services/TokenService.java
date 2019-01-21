package com.sagar.ccetmobileapp.services;

import android.content.SharedPreferences;

import com.sagar.ccetmobileapp.ApplicationScope;

import javax.inject.Inject;

/**
 * Created by SAGAR MAHOBIA on 03-Nov-18. at 01:01
 */

@ApplicationScope
public class TokenService {

    private SharedPreferences sharedPreferences;

    @Inject
    public TokenService(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public boolean hasToken() {
        return !getToken().isEmpty();
    }

    public void saveToken(String token) {
        sharedPreferences.edit().putString("token", token).apply();
    }

    public String getToken() {
        return sharedPreferences.getString("token", "");
    }

    public void removeToken() {
        this.saveToken("");
    }
}
