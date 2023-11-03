package com.sagar.ccetmobileapp.network.models;

import com.sagar.ccetmobileapp.network.models.serverentities.Notice;

import java.util.List;

/**
 * Created by SAGAR MAHOBIA on 14-Nov-18. at 13:02
 */
public class Notices {
    private List<Notice> notices;

    public List<Notice> getNotices() {
        return notices;
    }

    public void setNotices(List<Notice> notices) {
        this.notices = notices;
    }
}
