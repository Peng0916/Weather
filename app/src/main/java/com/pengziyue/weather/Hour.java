package com.pengziyue.weather;


import android.support.v4.app.Fragment;

import java.io.Serializable;


/**
 * A simple {@link Fragment} subclass.
 * 一小时的天气信息
 */
public class Hour implements Serializable {
    private String date;
    private String tmp;

    public Hour() {
    }

    public Hour(String date, String tmp) {
        this.date = date;
        this.tmp = tmp;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTmp() {
        return tmp;
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
    }
}
