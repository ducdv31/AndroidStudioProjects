package com.example.myhouse.datahistory;

import com.example.myhouse.timeconverter.TimeConverter;

public class DataKV {
    private String Key, Value;
    TimeConverter timeConverter;
    public DataKV(String key, String value) {
        Key = key;
        Value = value;
    }

    public String getKey() {
        timeConverter = new TimeConverter();
        return timeConverter.ConvertFromMinutes(Integer.parseInt(Key));
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }
}
