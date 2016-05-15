package com.pengziyue.weather;


import android.support.v4.app.Fragment;

import java.io.Serializable;


/**
 * A simple {@link Fragment} subclass.
 * 当前天气信息
 */
public class Now implements Serializable {

    private String city;
    private String cond;
    private String tmp;
    private String wind;

    public Now() {

    }

    public Now(String city, String cond, String tmp, String wind) {
        this.city = city;
        this.cond = cond;
        this.tmp = tmp;
        this.wind = wind;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCond() {
        return cond;
    }

    public void setCond(String cond) {
        this.cond = cond;
    }

    public String getTmp() {
        return tmp;
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }


}
