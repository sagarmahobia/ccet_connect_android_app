package com.sagar.ccetmobileapp.activities.notices;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.sagar.ccetmobileapp.Application;
import com.sagar.ccetmobileapp.R;
import com.sagar.ccetmobileapp.activities.notices.adapter.NoticesAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoticesActivity extends AppCompatActivity implements Contract.View {

    @Inject
    Contract.Presenter presenter;

    @Inject
    NoticesAdapter adapter;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.notices_recycler)
    RecyclerView noticesRecyclerView;

    @BindView(R.id.notices_error_message)
    TextView noticesErrorMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notices);

        ButterKnife.bind(this);

        ActionBar supportActionBar = getSupportActionBar();

        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        DaggerNoticesActivityComponent.builder()
                .applicationComponent(Application.getApplication(this).getApplicationComponent())
                .noticesActivityModule(new NoticesActivityModule(this))
                .build()
                .inject(this);

        getLifecycle().addObserver(presenter);
        swipeRefreshLayout.setOnRefreshListener(() -> presenter.load());
        noticesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        noticesRecyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == android.R.id.home) {
            this.onBackPressed();
        }
        return true;
    }

    @Override
    public void showProgress() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showNotices() {
        noticesRecyclerView.setVisibility(View.VISIBLE);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void hideNotices() {
        noticesRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void startNoticeViewerActivity(String url) {
        if (!(url.startsWith("http://") || url.startsWith("https://"))) {
            url = "http://" + url;
        }
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    @Override
    public void hideMessage() {
        noticesErrorMessages.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String msg) {
        noticesErrorMessages.setVisibility(View.VISIBLE);
        noticesErrorMessages.setText(msg);
    }
}
