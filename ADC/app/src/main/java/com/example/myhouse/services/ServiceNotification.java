package com.example.myhouse.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.myhouse.MainActivity;
import com.example.myhouse.datahistory.THValue;
import com.example.myhouse.notification.MyNotifications;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ServiceNotification extends Service {
    private final float temp_max = 40, temp_min = 10;
    private final int humi_max = 80, humi_min = 40;
    private Thread thread;

    public ServiceNotification() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        GetCurrentData();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        GetCurrentData();
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void GetCurrentData() {    /* Key: { t: value1, h: value2 } */
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("DHT11/Current")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            THValue thValue = snapshot.getValue(THValue.class);
                            assert thValue != null;
                            NotifyTH(thValue.getT(), thValue.getH());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }


    private void NotifyTH(int temp, int humidity) {
        MyNotifications myNotifications = new MyNotifications(this);
        // Notification
        if (temp > temp_max) {
            myNotifications.createNotification(MyNotifications.TEMP_HIGH,
                    String.valueOf(temp));
        } else if (temp < temp_min) {
            myNotifications.createNotification(MyNotifications.TEMP_LOW,
                    String.valueOf(temp));
        } else {
            myNotifications.createNotification(MyNotifications.TEMP_NORM,
                    String.valueOf(temp));
        }
        // Notification
        if (humidity > humi_max) {
            myNotifications.createNotification(MyNotifications.HUMI_HIGH,
                    String.valueOf(humidity));
        } else if (humidity < humi_min) {
            myNotifications.createNotification(MyNotifications.HUMI_LOW,
                    String.valueOf(humidity));
        } else {
            myNotifications.createNotification(MyNotifications.HUMI_NORM,
                    String.valueOf(humidity));
        }
    }
}