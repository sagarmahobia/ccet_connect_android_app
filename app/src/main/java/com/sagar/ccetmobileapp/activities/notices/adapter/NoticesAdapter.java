package com.sagar.ccetmobileapp.activities.notices.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sagar.ccetmobileapp.R;
import com.sagar.ccetmobileapp.activities.notices.Contract;
import com.sagar.ccetmobileapp.activities.notices.NoticeActivityScope;

import javax.inject.Inject;

/**
 * Created by SAGAR MAHOBIA on 14-Nov-18. at 20:19
 */

@NoticeActivityScope
public class NoticesAdapter extends RecyclerView.Adapter<NoticeViewHolder> {

    private Contract.Presenter presenter;

    @Inject
    NoticesAdapter(Contract.Presenter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public NoticeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.notice_item, parent, false);
        return new NoticeViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeViewHolder noticeViewHolder, int position) {
        presenter.onBindNotice(noticeViewHolder,position);
        noticeViewHolder.itemView.setOnClickListener(v -> {
            presenter.onNoticeItemClicked(position);
        });
    }

    @Override
    public int getItemCount() {
        return presenter.getNoticesCardSize();
    }
}
