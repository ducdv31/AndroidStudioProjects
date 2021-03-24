package com.example.myhouse.datahistory;

import com.example.myhouse.timeconverter.TimeConverter;

public class DataKKVV {

    private String Key, Key2, Value1, Value2;

    public DataKKVV(String key, String key2, String value1, String value2) {
        Key = key;
        Key2 = key2;
        Value1 = value1;
        Value2 = value2;
    }

    public String getDate() {
        return Key;
    }

    public String getKeyTime() {
        TimeConverter timeConverter = new TimeConverter();
        return timeConverter.ConvertFromMinutes(Integer.parseInt(Key2));
    }

    public String getValue1() {
        return Value1;
    }

    public String getValue2() {
        return Value2;
    }
}
