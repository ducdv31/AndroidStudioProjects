package com.example.myhouse.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.myhouse.MainActivity;
import com.example.myhouse.datahistory.THValue;
import com.example.myhouse.model.PmsModel;
import com.example.myhouse.notification.MyNotifications;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ServiceNotification extends Service {
    private final float temp_max = 40, temp_min = 10;
    private final int humi_max = 80, humi_min = 40;
    private final int pm1Min = 0;
    private final int pm1Max = 25;
    private Thread thread;
    MyNotifications myNotifications;

    public ServiceNotification() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("TAG", "onCreate: ");
        GetCurrentData();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("TAG", "onStartCommand: ");
        GetCurrentData();
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void GetCurrentData() {    /* Key: { t: value1, h: value2 } */
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("AHT10/Current")
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
        databaseReference.child("PMS7003/Current")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            PmsModel pmsModel = snapshot.getValue(PmsModel.class);
                            assert pmsModel != null;
                            notifyPMS(pmsModel.getPm1(),
                                    pmsModel.getPm25(),
                                    pmsModel.getPm10());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void notifyPMS(int pm1, int pm25, int pm10) {
        myNotifications = new MyNotifications(this);
        if (pm1 > pm1Max) {
            myNotifications.createNotification(MyNotifications.PM1_HIGH,
                    String.valueOf(pm1));
        } else if (pm1 < pm1Max){
            myNotifications.createNotification(MyNotifications.PMS1_NORM,
                    String.valueOf(pm1));
        }
        if (pm25 > pm1Max) {
            myNotifications.createNotification(MyNotifications.PM25_HIGH,
                    String.valueOf(pm25));
        } else if (pm25 < pm1Max){
            myNotifications.createNotification(MyNotifications.PMS25_NORM,
                    String.valueOf(pm25));
        }
        if (pm10 > pm1Max) {
            myNotifications.createNotification(MyNotifications.PM10_HIGH,
                    String.valueOf(pm10));
        } else if (pm10 < pm1Max){
            myNotifications.createNotification(MyNotifications.PMS10_NORM,
                    String.valueOf(pm10));
        }

    }

    private void checkData(int pm1, int pm1Min, int pm1Max) {
        myNotifications = new MyNotifications(this);
        if (pm1 > pm1Max) {
            myNotifications.createNotification(MyNotifications.PM1_HIGH,
                    String.valueOf(pm1));
        } else if (pm1 < pm1Max){
            myNotifications.createNotification(MyNotifications.PMS1_NORM,
                    String.valueOf(pm1));
        }
    }


    private void NotifyTH(int temp, int humidity) {
        myNotifications = new MyNotifications(this);
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