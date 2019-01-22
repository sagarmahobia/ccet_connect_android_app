package com.sagar.ccetmobileapp.activities.assignments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.sagar.ccetmobileapp.R;
import com.sagar.ccetmobileapp.network.models.Branches;
import com.sagar.ccetmobileapp.network.models.serverentities.Assignment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

public class AssignmentsActivity extends AppCompatActivity implements Contract.View {

    @Inject
    Contract.Presenter presenter;

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

    private int selectedBranch;
    private int selectedSemester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignments);

        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        getLifecycle().addObserver(presenter);
        swipeRefreshLayout.setOnRefreshListener(() -> presenter.load(selectedBranch, selectedSemester));
        setUpSpinners();
    }

    private void setUpSpinners() {


        List<String> semester = new ArrayList<>();
        semester.add(0, "Select Semester");
        for (int i = 1; i <= 8; i++) {
            semester.add(String.valueOf(i));
        }
        ArrayAdapter<String> semesterAdapter
                = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, semester);
        semesterSpinner.setAdapter(semesterAdapter);
        semesterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSemester = position;
                presenter.load(selectedBranch, selectedSemester);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        List<String> branchesList = Branches.getList();
        branchesList.add(0, "Select Branch");
        ArrayAdapter<String> branchesAdapter
                = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, branchesList);
        branchSpinner.setAdapter(branchesAdapter);
        branchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = branchesAdapter.getItem(position);
                selectedBranch = Branches.getBranch(item).getBranchId();
                presenter.load(selectedBranch, selectedSemester);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void showAssignments(List<Assignment> assignments) {
        assignmentContainer.removeAllViews();
        assignmentContainer.setVisibility(View.VISIBLE);
        for (Assignment assignment : assignments) {
            View inflate = LayoutInflater.from(this).inflate(R.layout.assignments_item_layout, assignmentContainer, false);

            TextView assignmentNo = inflate.findViewById(R.id.assignment_no);
            assignmentNo.setText(String.valueOf(assignment.getAssignmentNo()));

            TextView lastDate = inflate.findViewById(R.id.assignment_last_date);
            lastDate.setText(assignment.getLastDate());

            inflate.findViewById(R.id.download_button).setOnClickListener(v -> openLink(assignment.getLink()));
            assignmentContainer.addView(inflate);
        }
    }

    @Override
    public void hideAssignments() {
        assignmentContainer.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String message) {
        errorMessage.setVisibility(View.VISIBLE);
        errorMessage.setText(message);
    }

    @Override
    public void hideMessage() {
        errorMessage.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showProgress() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == android.R.id.home) {
            this.onBackPressed();
        }
        return true;
    }

    private void openLink(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}
