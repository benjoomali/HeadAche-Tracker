package com.benumali.android.headachetracker;

import java.util.Date;
import java.util.UUID;

//model - design pattern
public class Headache {

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private Date mTime;
    private boolean mInout;
    private String mDescription;

    public Headache () {
        this(UUID.randomUUID());
    }

    public Headache(UUID id) {
        mId = id;
        mDate = new Date();
        mTime = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public Date getTime() {
        return mTime;
    }

    public void setTime(Date time) {
        mTime = time;
    }

    public boolean isInout() {
        return mInout;
    }

    public void setInout(boolean inout) {
        mInout = inout;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }
}
