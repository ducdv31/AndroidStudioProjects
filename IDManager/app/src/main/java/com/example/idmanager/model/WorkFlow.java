package com.example.idmanager.model;

import com.example.idmanager.timeconverter.TimeConverter;

public class WorkFlow {
    private final String day;
    private final String start;
    private final String end;
    private final TimeConverter timeConverter;

    public WorkFlow(String day, String start, String end) {
        this.day = day;
        this.start = start;
        this.end = end;
        timeConverter = new TimeConverter();
    }

    public String getDay() {
        return day;
    }

    public String getStart() {
        return timeConverter.ConvertFromMinutes(Integer.parseInt(start));
    }

    public String getEnd() {
        return timeConverter.ConvertFromMinutes(Integer.parseInt(end));
    }
}
