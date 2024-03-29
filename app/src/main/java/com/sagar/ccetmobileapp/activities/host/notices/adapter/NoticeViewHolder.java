package com.sagar.ccetmobileapp.activities.host.notices.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.sagar.ccetmobileapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by SAGAR MAHOBIA on 14-Nov-18. at 20:18
 */
class NoticeViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.notice_title)
    TextView noticeTitle;

    NoticeViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    void setTitle(String title) {
        noticeTitle.setText(title);
    }
}
