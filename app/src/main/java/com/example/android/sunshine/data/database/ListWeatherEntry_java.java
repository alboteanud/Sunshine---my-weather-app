package com.example.android.sunshine.data.database;

import java.util.Date;

public class ListWeatherEntry_java {

    private int id;
    private int weatherIconId;
    private Date date;
    private double temp;
    private String icon;

    public ListWeatherEntry_java(int id, int weatherIconId, Date date, double temp, String icon) {
        this.id = id;
        this.weatherIconId = weatherIconId;
        this.date = date;
        this.temp = temp;
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public int getWeatherIconId() {
        return weatherIconId;
    }

    public Date getDate() {
        return date;
    }

    public double getTemp() {
        return temp;
    }

    public String getIcon() {
        return icon;
    }
}
