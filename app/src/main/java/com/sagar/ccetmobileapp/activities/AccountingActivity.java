package com.sagar.ccetmobileapp.activities;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.sagar.ccetmobileapp.Application;
import com.sagar.ccetmobileapp.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountingActivity extends AppCompatActivity implements Contract.View {

    @Inject
    Contract.Presenter presenter;


    /*Sign Up Container variables*/
    @BindView(R.id.sign_up_container)
    ConstraintLayout signUpContainer;

    @BindView(R.id.first_name)
    EditText firstNameET;

    @BindView(R.id.last_name)
    EditText lastNameET;

    @BindView(R.id.admission_year)
    Spinner admissionYearSpinner;

    @BindView(R.id.admission_semester)
    Spinner admissionSemSpinner;

    @BindView(R.id.email)
    EditText emailET;

    @BindView(R.id.password)
    EditText passwordET;

    @BindView(R.id.confirm_password)
    EditText confirmPasswordET;

    @BindView(R.id.sign_up_button)
    Button signUpButton;

    @BindView(R.id.already_have_account_button)
    Button alreadyHaveAccountButton;

    /*Sign In Container Variables*/
    @BindView(R.id.sign_in_container)
    ConstraintLayout signInContainer;

    @BindView(R.id.sign_in_email)
    EditText signInEmailET;

    @BindView(R.id.sign_in_password)
    EditText signInPasswordText;

    @BindView(R.id.sign_in_button)
    Button signInButton;

    @BindView(R.id.dont_have_account)
    Button dontHaveAccountButton;

    /*Sign Up Otp Variables*/
    @BindView(R.id.sign_up_otp_container)
    ConstraintLayout signUpOtpContainer;

    @BindView(R.id.sign_up_otp)
    EditText signUpOtp;

    @BindView(R.id.confirm_otp_button)
    Button confirmOtpButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_accounting);

        ButterKnife.bind(this);

        DaggerAccountingActivityComponent.
                builder().
                applicationComponent(Application.getApplication(this).getApplicationComponent()).
                accountingActivityModule(new AccountingActivityModule(this)).
                build().
                inject(this);

        getLifecycle().addObserver(presenter);

        String[] semesters = getResources().getStringArray(R.array.semesters);

        ArrayAdapter<String> sa = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                semesters);
        sa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        admissionSemSpinner.setAdapter(sa);

        String[] years = getResources().getStringArray(R.array.years);

        ArrayAdapter<String> ya = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                years);
        ya.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        admissionYearSpinner.setAdapter(ya);

        setUpSignUpButton();
        setUpSignInButton();
        setUpDontHaveAccountButton();
        setUpSignUpOtpButton();
        setUpAlreadyHaveAccountButton();
    }


    private void setUpSignUpButton() {
        signUpButton.setOnClickListener(v -> {
            String firstName = firstNameET.getText().toString();
            String lastName = lastNameET.getText().toString();

            String email = emailET.getText().toString();
            String password = passwordET.getText().toString();
            String confirmPassword = confirmPasswordET.getText().toString();

            int selectedItemPosition = admissionYearSpinner.getSelectedItemPosition();
            int selectedItemPosition1 = admissionSemSpinner.getSelectedItemPosition();


            if (selectedItemPosition == 0 || selectedItemPosition == AdapterView.INVALID_POSITION
                    || selectedItemPosition1 == 0 || selectedItemPosition1 == AdapterView.INVALID_POSITION
                    ) {
                //todo invalid input
                return;
            }
            String admissionSem = admissionSemSpinner.getSelectedItem().toString().substring(0, 1);
            String admissionYear = admissionYearSpinner.getSelectedItem().toString();

            presenter.onSingUp(firstName, lastName, email, password, confirmPassword, admissionYear, admissionSem);
        });
    }

    private void setUpSignUpOtpButton() {
        confirmOtpButton.setOnClickListener(v -> {
            String otp = signUpOtp.getText().toString();
            presenter.onSignUpOtpSubmit(otp);
        });
    }

    private void setUpSignInButton() {
        signInButton.setOnClickListener(v -> {
            String loginEmail = signInEmailET.getText().toString();
            String loginPassword = signInPasswordText.getText().toString();
            presenter.onSignIn(loginEmail, loginPassword);

        });
    }

    private void setUpAlreadyHaveAccountButton() {
        alreadyHaveAccountButton.setOnClickListener(v -> {
            signUpContainer.setVisibility(View.GONE);
            signInContainer.setVisibility(View.VISIBLE);
        });
    }

    private void setUpDontHaveAccountButton() {
        dontHaveAccountButton.setOnClickListener(v -> {
            signInContainer.setVisibility(View.GONE);
            signUpContainer.setVisibility(View.VISIBLE);
        });

    }

    @Override
    public void askForOtp() {
        signUpContainer.setVisibility(View.GONE);
        signUpOtpContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSuccessSignIn() {
        //todo
        Log.d("Signed","In");
    }
}
























