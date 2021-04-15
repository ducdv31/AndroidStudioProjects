package com.example.imqtt;

import android.app.Application;

import com.example.imqtt.sharedpreference.DataLocalManager;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DataLocalManager.init(getApplicationContext());
    }
}
