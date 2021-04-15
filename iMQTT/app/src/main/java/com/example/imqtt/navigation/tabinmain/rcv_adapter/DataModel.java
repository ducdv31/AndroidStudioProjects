package com.example.imqtt.navigation.tabinmain.rcv_adapter;

import java.io.Serializable;

public class DataModel implements Serializable {
    private String time;
    private String topic;
    private String content;

    public DataModel(String time, String topic, String content) {
        this.time = time;
        this.topic = topic;
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
