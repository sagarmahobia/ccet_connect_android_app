package com.sagar.ccetmobileapp.activities.host.notices.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sagar.ccetmobileapp.R;
import com.sagar.ccetmobileapp.activities.host.notices.NoticeFragmentScope;
import com.sagar.ccetmobileapp.network.models.serverentities.Notice;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by SAGAR MAHOBIA on 14-Nov-18. at 20:19
 */

@NoticeFragmentScope
public class NoticesAdapter extends RecyclerView.Adapter<NoticeViewHolder> {

    private List<Notice> notices;

    private OnItemClickListener listener;

    @Inject
    NoticesAdapter() {
    }

    public void setNotices(List<Notice> notices) {
        this.notices = notices;
        notifyDataSetChanged();
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public NoticeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.notice_item, parent, false);
        return new NoticeViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeViewHolder noticeViewHolder, int position) {
        Notice notice = notices.get(position);
        View itemView = noticeViewHolder.itemView;

        noticeViewHolder.setTitle(notice.getTitle());
        itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onClick(notice);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notices != null ? notices.size() : 0;
    }

    public interface OnItemClickListener {
        void onClick(Notice notice);
    }
}
