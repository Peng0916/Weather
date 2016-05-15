package com.pengziyue.weather;


import android.support.v4.app.Fragment;

import java.io.Serializable;


/**
 * A simple {@link Fragment} subclass.
 * 一天的天气信息
 */
public class Daily implements Serializable {
    private String date;
    private String max;
    private String min;

    public Daily() {
    }

    public Daily(String date, String max, String min) {
        this.date = date;
        this.max = max;
        this.min = min;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }
}
