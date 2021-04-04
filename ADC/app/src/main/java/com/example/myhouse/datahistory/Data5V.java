package com.example.myhouse.datahistory;

import com.example.myhouse.timeconverter.TimeConverter;

public class Data5V {
    private final String date;
    private final String time;
    private final String pm1;
    private final String pm25;
    private final String pm10;

    public Data5V(String date, String time, String pm1, String pm25, String pm10) {
        this.date = date;
        this.time = time;
        this.pm1 = pm1;
        this.pm25 = pm25;
        this.pm10 = pm10;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        TimeConverter timeConverter = new TimeConverter();
        return timeConverter.ConvertFromMinutes(Integer.parseInt(time));
    }

    public String getPm1() {
        return pm1;
    }

    public String getPm25() {
        return pm25;
    }

    public String getPm10() {
        return pm10;
    }
}
