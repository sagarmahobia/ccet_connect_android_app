package com.sagar.ccetmobileapp.activities.host.account.otp;

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


public class OtpFragment extends Fragment {

    @Inject
    OtpFragmentViewModelFactory viewModelFactory;

    @Inject
    ErrorConverterService errorConverterService;

    /*Sign Up Otp Variables*/
    @BindView(R.id.sign_up_otp_container)
    ConstraintLayout signUpOtpContainer;

    @BindView(R.id.sign_up_otp)
    EditText signUpOtp;

    @BindView(R.id.confirm_otp_button)
    Button confirmOtpButton;

    @BindView(R.id.account_progress_bar)
    ProgressBar progress;

    private OtpFragmentViewModel viewModel;
    private int otpId;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();

        if (arguments != null) {
            otpId = arguments.getInt(ConstantKey.OTP_ID);
        } else {
            throw new IllegalStateException("Otp id must be provided");
        }

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(OtpFragmentViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_otp, container, false);
        ButterKnife.bind(this, inflate);
        return inflate;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel.getOtpResponse().observe(this, this::onOtpResponse);
        setUpOtpButton();
    }

    private void onOtpResponse(Response<Boolean> response) {
        if (response.getStatus() == Status.SUCCESS) {
            hideProgress();
            boolean data = response.getData();
            if (data) {
                onSuccessOtpValidation();
            }
        } else if (response.getStatus() == Status.ERROR) {
            hideProgress();
            showOtpScreen();
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

    private void setUpOtpButton() {
        confirmOtpButton.setOnClickListener(v -> {
            String otp = signUpOtp.getText().toString();
            viewModel.onSignUpOtpSubmit(otp, otpId);
        });
    }

    private void showOtpScreen() {
        signUpOtpContainer.setVisibility(View.VISIBLE);

    }

    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
        signUpOtpContainer.setVisibility(View.GONE);
    }

    public void hideProgress() {
        progress.setVisibility(View.GONE);
    }

    public void onSuccessOtpValidation() {
        showMessage("Otp verified");
        Navigation.findNavController(signUpOtpContainer).navigate(R.id.popUpToHomeFragment);

    }

    public void showMessage(String text) {
        Toast.makeText(this.getContext(), text, Toast.LENGTH_SHORT).show();
    }

}
