package com.example.vitalyyurenya.appetizer.models;


import android.support.annotation.DrawableRes;

public class ActivityItem {
    @DrawableRes
    private int drawableId;

    private String event;
    private String timeAgo;

    public ActivityItem(int drawableId, String event, String timeAgo) {
        this.drawableId = drawableId;
        this.event = event;
        this.timeAgo = timeAgo;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getTimeAgo() {
        return timeAgo;
    }

    public void setTimeAgo(String timeAgo) {
        this.timeAgo = timeAgo;
    }
}
