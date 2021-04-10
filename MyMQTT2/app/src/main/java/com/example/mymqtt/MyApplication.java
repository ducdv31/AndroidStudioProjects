package com.example.mymqtt;

import android.app.Application;

import com.example.mymqtt.sharedpreference.DataLocalManager;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DataLocalManager.init(getApplicationContext());
    }
}
