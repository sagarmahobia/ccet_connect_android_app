package com.sagar.ccetmobileapp.activities.host.account.login;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.sagar.ccetmobileapp.R;
import com.sagar.ccetmobileapp.network.errors.ErrorCode;
import com.sagar.ccetmobileapp.network.models.Error;
import com.sagar.ccetmobileapp.responsemodel.Response;
import com.sagar.ccetmobileapp.responsemodel.Status;
import com.sagar.ccetmobileapp.services.ErrorConverterService;

import java.io.IOException;

import javax.inject.Inject;

import androidx.navigation.Navigation;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import okhttp3.ResponseBody;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    @Inject
    LoginFragmentViewModelFactory viewModelFactory;

    @Inject
    ErrorConverterService errorConverterService;

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

    @BindView(R.id.account_progress_bar)
    ProgressBar progress;

    private LoginFragmentViewModel viewModel;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginFragmentViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, inflate);
        return inflate;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel.getLoginResponse().observe(this, this::onLoginResponse);
        setUpSignInButton();
        setUpDontHaveAccountButton();
    }

    private void onLoginResponse(Response<Boolean> response) {
        if (response.getStatus() == Status.SUCCESS) {
            hideProgress();
            boolean data = response.getData();
            if (data) {
                onSuccessSignIn();
            }
        } else if (response.getStatus() == Status.ERROR) {
            hideProgress();
            showSignInScreen();
            Throwable error = response.getError();
            if (error instanceof HttpException) {
                ResponseBody responseBody = ((HttpException) error).response().errorBody();
                if (responseBody != null) {
                    Error e;
                    try {
                        e = errorConverterService.getError(responseBody.string());
                    } catch (IOException e1) {
                        return;
                    }

                    ErrorCode errorCode = ErrorCode.getErrorCode(e.getErrorCode());
                    switch (errorCode) {
                        case InvalidInputException:
                            showMessage("Please enter valid info");
                            break;
                        case UserNotFoundException:
                            showMessage("User not found");
                            break;
                        default:
                            showMessage("Something went wrong.");
                    }
                }
            }

        } else if (response.getStatus() == Status.LOADING) {
            showProgress();
        }
    }

    private void setUpSignInButton() {
        signInButton.setOnClickListener(v -> {
            String loginEmail = signInEmailET.getText().toString();
            String loginPassword = signInPasswordText.getText().toString();
            viewModel.onSignIn(loginEmail, loginPassword);
        });
    }

    private void setUpDontHaveAccountButton() {
        dontHaveAccountButton.setOnClickListener(v -> Navigation.findNavController(dontHaveAccountButton).navigate(R.id.action_loginFragment_to_signUpFragment));
    }

    public void showSignInScreen() {
        signInContainer.setVisibility(View.VISIBLE);
    }
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
        signInContainer.setVisibility(View.GONE);
    }

    public void hideProgress() {
        progress.setVisibility(View.GONE);
    }

    public void onSuccessSignIn() {
        showMessage("You have signed in");
        Navigation.findNavController(signInContainer).navigateUp();
    }

    public void showMessage(String text) {
        Toast.makeText(this.getContext(), text, Toast.LENGTH_SHORT).show();
    }
}
