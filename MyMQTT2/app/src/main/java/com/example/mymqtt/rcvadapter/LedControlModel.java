package com.example.mymqtt.rcvadapter;

public class LedControlModel {
    private int number;
    private String topic;

    public LedControlModel(int number, String topic) {
        this.number = number;
        this.topic = topic;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
