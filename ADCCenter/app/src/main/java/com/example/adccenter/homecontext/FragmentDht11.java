package com.example.adccenter.homecontext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ekn.gruzer.gaugelibrary.ArcGauge;
import com.ekn.gruzer.gaugelibrary.FullGauge;
import com.ekn.gruzer.gaugelibrary.Range;
import com.example.adccenter.notification.MyNotifications;
import com.example.adccenter.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.Objects;


public class FragmentDht11 extends Fragment {
    private ArcGauge Temp;
    private FullGauge Humi;
    private final float temp_max = 40, temp_min = 10;
    private final int humi_max = 80, humi_min = 40;
    private final DatabaseReference databaseReference = FirebaseDatabase
            .getInstance()
            .getReference();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View dht11View = inflater.inflate(R.layout.fragment_dht11, container, false);

        Temp = dht11View.findViewById(R.id.temperature);
        Humi = dht11View.findViewById(R.id.humidity);
        Range Humidity = new Range();
        Humidity.setColor(Color.GREEN);
        Humidity.setFrom(0);
        Humidity.setTo(100);
        Range Temperature = new Range();
        Temperature.setColor(Color.BLUE);
        Temperature.setFrom(0);
        Temperature.setTo(100);
        Temp.setValueColor(Color.BLACK);
        Temp.addRange(Temperature);
        Humi.setValueColor(Color.BLACK);
        Humi.addRange(Humidity);
        MyNotifications myNotifications = new MyNotifications(getActivity());
        final DecimalFormat format = new DecimalFormat("#.0");
        databaseReference.child("DHT11/t").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String t = Objects.requireNonNull(snapshot.getValue()).toString();
                double temp = Double.parseDouble(t);
                temp = (double) Math.round(temp * 10) / 10;
                Temp.setValue(temp);

                if (temp > temp_max) {
                    myNotifications.createNotification(myNotifications.TempHigh(),
                            String.valueOf(temp));
                } else if (temp < temp_min) {
                    myNotifications.createNotification(myNotifications.TempLow(),
                            String.valueOf(temp));
                } else {
                    myNotifications.createNotification(myNotifications.TempNorm(),
                            String.valueOf(temp));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Temp.setValue(0);
            }
        });

        databaseReference.child("DHT11/h").addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String humi = Objects.requireNonNull(snapshot.getValue()).toString();
                int humidity = Integer.parseInt(humi);
                Humi.setValue(humidity);

                if (humidity > humi_max) {
                    myNotifications.createNotification(myNotifications.HumiHigh(),
                            String.valueOf(humidity));
                } else if (humidity < humi_min) {
                    myNotifications.createNotification(myNotifications.HumiLow(),
                            String.valueOf(humidity));
                } else {
                    myNotifications.createNotification(myNotifications.HumiNorm(),
                            String.valueOf(humidity));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Humi.setValue(0);
            }
        });

        return dht11View;
    }
}