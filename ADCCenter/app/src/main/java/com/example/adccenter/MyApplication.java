package com.example.adccenter;

import android.app.Application;

import com.example.adccenter.DataLocal.DataLocalManager;
import com.example.adccenter.notification.MyNotifications;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DataLocalManager.init(getApplicationContext());
//        MyNotifications myNotifications = new MyNotifications(getApplicationContext());
//        myNotifications.createNotificationChannels();
    }

}
