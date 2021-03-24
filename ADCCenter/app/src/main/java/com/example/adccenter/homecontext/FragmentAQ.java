package com.example.adccenter.homecontext;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ekn.gruzer.gaugelibrary.HalfGauge;
import com.ekn.gruzer.gaugelibrary.Range;
import com.example.adccenter.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;


public class FragmentAQ extends Fragment {

    private HalfGauge Mq135, Pm25;

    private final DatabaseReference databaseReference = FirebaseDatabase
            .getInstance()
            .getReference();

    public FragmentAQ() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View AQView = inflater.inflate(R.layout.fragment_aq, container, false);
        Mq135 = AQView.findViewById(R.id.mq135);
        Pm25 = AQView.findViewById(R.id.pm25);
        Range range1 = new Range();
        Range range2 = new Range();
        Range range3 = new Range();
        Mq135.setMaxValue(100);
        Mq135.setMinValue(0);
        Pm25.setMaxValue(100);
        Pm25.setMinValue(0);

        range1.setColor(Color.GREEN);
        range1.setFrom(0);
        range1.setTo(30);
        range2.setColor(Color.YELLOW);
        range2.setFrom(30);
        range2.setTo(70);
        range3.setColor(Color.RED);
        range3.setFrom(70);
        range3.setTo(100);
        Mq135.addRange(range1);
        Mq135.addRange(range2);
        Mq135.addRange(range3);
        Pm25.addRange(range1);
        Pm25.addRange(range2);
        Pm25.addRange(range3);

        databaseReference.child("AQ/mq135").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String MQ135 = Objects.requireNonNull(snapshot.getValue()).toString();
                double mq135 = Double.parseDouble(MQ135);
                Mq135.setValue(mq135);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Mq135.setValue(0);
            }
        });
        databaseReference.child("AQ/pm25").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String PM25 = Objects.requireNonNull(snapshot.getValue()).toString();
                double pm25 = Double.parseDouble(PM25);
                Pm25.setValue(pm25);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Pm25.setValue(0);
            }
        });
        return AQView;
    }
}