package com.sagar.ccetmobileapp.activities.host.notices;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sagar.ccetmobileapp.R;
import com.sagar.ccetmobileapp.activities.host.notices.adapter.NoticesAdapter;
import com.sagar.ccetmobileapp.network.models.serverentities.Notice;
import com.sagar.ccetmobileapp.responsemodel.Response;
import com.sagar.ccetmobileapp.responsemodel.Status;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoticesFragment extends Fragment {

    @Inject
    NoticesViewModelFactory factory;

    @Inject
    NoticesAdapter adapter;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.notices_recycler)
    RecyclerView noticesRecyclerView;

    @BindView(R.id.notices_error_message)
    TextView noticesErrorMessages;

    private NoticesFragmentViewModel viewModel;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this, factory).get(NoticesFragmentViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_notices, container, false);
        ButterKnife.bind(this, inflate);
        return inflate;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        swipeRefreshLayout.setOnRefreshListener(() -> viewModel.load());
        noticesRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        noticesRecyclerView.setAdapter(adapter);

        adapter.setListener(notice -> openNoticeURL(notice.getLink()));

        viewModel.getListResponse().observe(this, this::onListResponse);
    }

    private void onListResponse(Response<List<Notice>> response) {
        if (response.getStatus() == Status.SUCCESS) {
            hideProgress();
            List<Notice> data = response.getData();
            if (!data.isEmpty()) {
                hideMessage();
                showNotices();
                adapter.setNotices(data);
            } else {
                hideNotices();
                showMessage("No notice available");
            }
        } else if (response.getStatus() == Status.ERROR) {
            hideProgress();
            hideNotices();
            showMessage("Something went wrong.");
        } else if (response.getStatus() == Status.LOADING) {
            showProgress();
            hideMessage();
            hideNotices();
        }
    }


    public void showProgress() {
        swipeRefreshLayout.setRefreshing(true);
    }

    public void hideProgress() {
        swipeRefreshLayout.setRefreshing(false);
    }

    public void showNotices() {
        noticesRecyclerView.setVisibility(View.VISIBLE);
        adapter.notifyDataSetChanged();
    }

    public void hideNotices() {
        noticesRecyclerView.setVisibility(View.GONE);
    }

    public void openNoticeURL(String url) {
        if (!(url.startsWith("http://") || url.startsWith("https://"))) {
            url = "http://" + url;
        }
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    public void hideMessage() {
        noticesErrorMessages.setVisibility(View.GONE);
    }

    public void showMessage(String msg) {
        noticesErrorMessages.setVisibility(View.VISIBLE);
        noticesErrorMessages.setText(msg);
    }

}
