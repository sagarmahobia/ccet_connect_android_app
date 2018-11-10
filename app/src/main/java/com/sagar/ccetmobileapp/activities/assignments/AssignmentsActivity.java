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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sagar.ccetmobileapp.Application;
import com.sagar.ccetmobileapp.R;
import com.sagar.ccetmobileapp.network.models.assignments.Assignment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AssignmentsActivity extends AppCompatActivity implements Contract.View {

    @Inject
    Contract.Presenter presenter;

    @BindView(R.id.assignments_container)
    LinearLayout assignmentContainer;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.error_message)
    TextView errorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignments);

        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        DaggerAssignmentsComponent.builder()
                .applicationComponent(Application.getApplication(this).getApplicationComponent())
                .assignmentsModule(new AssignmentsModule(this))
                .build()
                .inject(this);

        getLifecycle().addObserver(presenter);
        swipeRefreshLayout.setOnRefreshListener(() -> presenter.load());
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
        assignmentContainer.setVerticalGravity(View.GONE);
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
