package com.sagar.ccetmobileapp.activities.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.sagar.ccetmobileapp.Application;
import com.sagar.ccetmobileapp.R;
import com.sagar.ccetmobileapp.activities.account.AccountingActivity;
import com.sagar.ccetmobileapp.activities.assignments.AssignmentsActivity;
import com.sagar.ccetmobileapp.services.TokenService;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Contract.View {

    @Inject
    Contract.Presenter presenter;

    @Inject
    TokenService tokenService;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    /*Menu */

    @BindView(R.id.notice_board_card)
    CardView noticeBoardCard;

    @BindView(R.id.assignment_card)
    CardView assignmentsCard;

    @BindView(R.id.syllabus_card)
    CardView syllabusCard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        DaggerHomeActivityComponent.builder()
                .applicationComponent(Application.getApplication(this).getApplicationComponent())
                .homeActivityModule(new HomeActivityModule(this))
                .build().inject(this);

        getLifecycle().addObserver(presenter);

        noticeBoardCard.setOnClickListener(v -> {
            //todo
        });

        assignmentsCard.setOnClickListener(v -> {
            if (tokenService.hasToken()) {
                startNewActivity(AssignmentsActivity.class);
            } else {
                startNewActivity(AccountingActivity.class);
            }
        });

        syllabusCard.setOnClickListener(v -> {
            //todo
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.profile) {
            if (tokenService.hasToken()) {
                showMessage("Already Signed In");
            } else {
                startNewActivity(AccountingActivity.class);
            }
        } else if (id == R.id.log_out) {
            if (tokenService.hasToken()) {
                tokenService.removeToken();
                showMessage("Signed out");
            } else {
                showMessage("Already signed out");
            }
        }

        return true;
    }

    private void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void startNewActivity(Class aClass) {
        Intent intent = new Intent(this, aClass);
        startActivity(intent);
    }

}
