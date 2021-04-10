package com.example.sharedpreference;

import android.app.Application;

import com.example.sharedpreference.sharedpreference.DataLocalManager;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DataLocalManager.init(getApplicationContext());
    }
}
