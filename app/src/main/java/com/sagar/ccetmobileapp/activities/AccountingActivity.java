package com.sagar.ccetmobileapp.activities;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

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
    ScrollView signUpContainer;

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


    @BindView(R.id.account_progress_bar)
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_accounting);

        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

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
                showMessage("Please select valid admission year and semester.");
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
        alreadyHaveAccountButton.setOnClickListener(v -> showSignInScreen());
    }

    private void setUpDontHaveAccountButton() {
        dontHaveAccountButton.setOnClickListener(v -> showSignUpScreen());

    }


    @Override
    public void showSignUpScreen() {
        signUpContainer.setVisibility(View.VISIBLE);
        signUpOtpContainer.setVisibility(View.GONE);
        signInContainer.setVisibility(View.GONE);
    }

    @Override
    public void showSignInScreen() {
        signUpContainer.setVisibility(View.GONE);
        signUpOtpContainer.setVisibility(View.GONE);
        signInContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void showSignUpOtpScreen() {
        signUpContainer.setVisibility(View.GONE);
        signUpOtpContainer.setVisibility(View.VISIBLE);
        signInContainer.setVisibility(View.GONE);
    }

    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
        signUpContainer.setVisibility(View.GONE);
        signUpOtpContainer.setVisibility(View.GONE);
        signInContainer.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.GONE);
    }

    @Override
    public void onSuccessSignIn() {
        showMessage("You have signed in");
        this.finish();
    }

    @Override
    public void showMessage(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

}
