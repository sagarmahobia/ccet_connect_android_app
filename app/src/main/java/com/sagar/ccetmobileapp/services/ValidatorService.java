package com.sagar.ccetmobileapp.services;

import com.sagar.ccetmobileapp.ApplicationScope;

import java.util.regex.Pattern;

import javax.inject.Inject;

/**
 * Created by SAGAR MAHOBIA on 30-Oct-18. at 23:30
 */

@ApplicationScope
public class ValidatorService {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\." +
            "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$";

    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$";

    private final Pattern emailPattern;

    private final Pattern passwordPattern;

    @Inject
    public ValidatorService() {
        emailPattern = Pattern.compile(EMAIL_REGEX);
        passwordPattern = Pattern.compile(PASSWORD_REGEX);
    }

    public boolean isValidEmail(String email) {
        return email != null && emailPattern.matcher(email).matches();
    }

    public boolean isValidPassword(String password1, String password2) {
        boolean matches = passwordPattern.matcher(password1).matches();
        return password1 != null
                && password2 != null
                && password1.equals(password2)
                && matches;
    }

    public boolean isValidPassword(String password1) {
        return password1 != null
                && passwordPattern.matcher(password1).matches();
    }


}
