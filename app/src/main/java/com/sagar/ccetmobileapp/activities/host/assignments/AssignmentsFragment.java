package com.sagar.ccetmobileapp.activities.host.assignments;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.sagar.ccetmobileapp.R;
import com.sagar.ccetmobileapp.network.models.Branches;
import com.sagar.ccetmobileapp.network.models.serverentities.Assignment;
import com.sagar.ccetmobileapp.responsemodel.Response;
import com.sagar.ccetmobileapp.responsemodel.Status;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

/**
 * A simple {@link Fragment} subclass.
 */
public class AssignmentsFragment extends Fragment {


    @Inject
    AssignmentsFragmentViewModelFactory viewModelFactory;

    @BindView(R.id.assignments_container)
    LinearLayout assignmentContainer;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.branch_spinner)
    Spinner branchSpinner;

    @BindView(R.id.semester_spinner)
    Spinner semesterSpinner;

    @BindView(R.id.error_message)
    TextView errorMessage;

    private AssignmentsFragmentViewModel viewModel;

    private int selectedBranch;
    private int selectedSemester;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(AssignmentsFragmentViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_assignments, container, false);
        ButterKnife.bind(this, inflate);
        return inflate;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpSpinners();
        viewModel.getListResponse().observe(this, this::onListResponse);
    }

    @SuppressWarnings("ConstantConditions")
    private void setUpSpinners() {

        List<String> semester = new ArrayList<>();
        semester.add(0, "Select Semester");
        for (int i = 1; i <= 8; i++) {
            semester.add(String.valueOf(i));
        }
        ArrayAdapter<String> semesterAdapter
                = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, semester);
        semesterSpinner.setAdapter(semesterAdapter);
        semesterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSemester = position;
                if (position == 0) {
                    showMessage("Select your branch and semester");
                    return;
                }
                viewModel.load(selectedBranch, selectedSemester);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        List<String> branchesList = Branches.getList();
        branchesList.add(0, "Select Branch");
        ArrayAdapter<String> branchesAdapter
                = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, branchesList);
        branchSpinner.setAdapter(branchesAdapter);
        branchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    selectedBranch = 0;
                    showMessage("Select your branch and semester");
                    return;
                }
                String item = branchesAdapter.getItem(position);
                selectedBranch = Branches.getBranch(item).getBranchId();
                viewModel.load(selectedBranch, selectedSemester);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void onListResponse(Response<List<Assignment>> response) {
        if (response.getStatus() == Status.SUCCESS) {
            hideProgress();
            List<Assignment> data = response.getData();
            if (!data.isEmpty()) {
                hideMessage();
                showAssignments(data);
            } else {
                hideAssignments();
                showMessage("No assignments available");
            }
        } else if (response.getStatus() == Status.ERROR) {
            hideProgress();
            hideAssignments();
            showMessage("Something went wrong.");
        } else if (response.getStatus() == Status.LOADING) {
            showProgress();
            hideMessage();
            hideAssignments();
        }
    }


    public void showAssignments(List<Assignment> assignments) {
        assignmentContainer.removeAllViews();
        assignmentContainer.setVisibility(View.VISIBLE);
        for (Assignment assignment : assignments) {
            View inflate = LayoutInflater.from(this.getContext()).inflate(R.layout.assignments_item_layout, assignmentContainer, false);

            TextView assignmentNo = inflate.findViewById(R.id.assignment_no);
            assignmentNo.setText(String.valueOf(assignment.getAssignmentNo()));

            TextView lastDate = inflate.findViewById(R.id.assignment_last_date);
            lastDate.setText(assignment.getLastDate());

            inflate.findViewById(R.id.download_button).setOnClickListener(v -> openLink(assignment.getLink()));
            assignmentContainer.addView(inflate);
        }
    }

    public void hideAssignments() {
        assignmentContainer.setVisibility(View.GONE);
    }

    public void showMessage(String message) {
        errorMessage.setVisibility(View.VISIBLE);
        errorMessage.setText(message);
    }

    public void hideMessage() {
        errorMessage.setVisibility(View.GONE);
    }

    public void hideProgress() {
        swipeRefreshLayout.setRefreshing(false);
    }

    public void showProgress() {
        swipeRefreshLayout.setRefreshing(true);
    }

    private void openLink(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

}
