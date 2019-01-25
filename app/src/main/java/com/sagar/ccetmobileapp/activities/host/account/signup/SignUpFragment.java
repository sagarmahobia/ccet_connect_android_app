package com.sagar.ccetmobileapp.activities.host.account.signup;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.sagar.ccetmobileapp.constants.ConstantKey;
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
public class SignUpFragment extends Fragment {


    @Inject
    ErrorConverterService errorConverterService;

    @Inject
    SignUpFragmentViewModelFactory viewModelFactory;

    /*Sign Up Container variables*/
    @BindView(R.id.sign_up_container)
    View signUpContainer;

    @BindView(R.id.email)
    EditText emailET;

    @BindView(R.id.password)
    EditText passwordET;

    @BindView(R.id.confirm_password)
    EditText confirmPasswordET;

    @BindView(R.id.sign_up_button)
    Button signUpButton;

    @BindView(R.id.account_progress_bar)
    ProgressBar progress;

    private SignUpFragmentViewModel viewModel;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SignUpFragmentViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_sign_up, container, false);
        ButterKnife.bind(this, inflate);
        return inflate;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpSignUpButton();
        viewModel.getSignUpResponse().observe(this, this::onSignUpResponse);

    }

    private void onSignUpResponse(Response<Integer> response) {
        if (response.getStatus() == Status.SUCCESS) {
            hideProgress();
            int data = response.getData();
            showSignUpOtpScreen(data);
        } else if (response.getStatus() == Status.ERROR) {
            hideProgress();
            showSignUpScreen();
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
                    showSignUpScreen();
                    ErrorCode errorCode = ErrorCode.getErrorCode(e.getErrorCode());
                    switch (errorCode) {
                        case InvalidInputException:
                            showMessage("Please enter valid info");
                            break;
                        case EmailAlreadyUsedException:
                            showMessage("Email already is use. Please provide a different email");
                            break;
                        case NULL:
                        default:
                            showMessage("Something went wrong.");
                    }
                }
            }

        } else if (response.getStatus() == Status.LOADING) {
            showProgress();
        }
    }

    private void setUpSignUpButton() {
        signUpButton.setOnClickListener(v -> {

            String email = emailET.getText().toString();
            String password = passwordET.getText().toString();
            String confirmPassword = confirmPasswordET.getText().toString();

            viewModel.onSingUp(email, password, confirmPassword);
        });
    }

    public void showSignUpScreen() {
        signUpContainer.setVisibility(View.VISIBLE);
    }

    public void showSignUpOtpScreen(int data) {
        Bundle bundle = new Bundle();
        bundle.putInt(ConstantKey.OTP_ID, data);
        Navigation.findNavController(signUpContainer).navigate(R.id.action_signUpFragment_to_otpFragment, bundle);
    }

    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
        signUpContainer.setVisibility(View.GONE);
    }

    public void hideProgress() {
        progress.setVisibility(View.GONE);
    }

    public void showMessage(String text) {
        Toast.makeText(this.getContext(), text, Toast.LENGTH_SHORT).show();
    }

}
