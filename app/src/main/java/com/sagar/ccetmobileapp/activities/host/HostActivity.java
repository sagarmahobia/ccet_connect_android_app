package com.sagar.ccetmobileapp.activities.host;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.sagar.ccetmobileapp.R;

import javax.inject.Inject;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

import static androidx.navigation.Navigation.findNavController;
import static androidx.navigation.ui.NavigationUI.navigateUp;
import static androidx.navigation.ui.NavigationUI.setupActionBarWithNavController;
import static androidx.navigation.ui.NavigationUI.setupWithNavController;


/**
 * Created by SAGAR MAHOBIA on 19-Jan-19. at 19:35
 */
public class HostActivity extends AppCompatActivity
        implements HasSupportFragmentInjector, NavigationView.OnNavigationItemSelectedListener {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_host);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        navigationView.setNavigationItemSelectedListener(this);


        NavHostFragment finalHost = NavHostFragment.create(R.navigation.mobile_navigation);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_view, finalHost)
                .setPrimaryNavigationFragment(finalHost) // this is the equivalent to app:defaultNavHost="true"
                .commitNow();

        setUp(finalHost);
    }

    private void setUp(NavHostFragment finalHost) {

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        NavController navController = finalHost.getNavController();
        setupActionBarWithNavController(this, navController, drawer);
        setupWithNavController(navigationView, navController);
    }


    public boolean onSupportNavigateUp() {
        return navigateUp(findNavController(this, R.id.fragment_view), drawer);
    }

    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        menuItem.setChecked(true);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
