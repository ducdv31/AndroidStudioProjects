package com.example.adccenter.DataDatabase;

import android.annotation.SuppressLint;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;

// Get data
public class GetDatabase extends Database {
    private TextView textView;
    private String child, unit;
    private String Second, Minute, Hour, Timer;
    public String data;

    GetDatabase() {

    }

    public GetDatabase(String child) {
        this.child = child;
    }

    public GetDatabase(String child, String data) {
        this.child = child;
        this.data = data;
    }

    public GetDatabase(TextView textView, String child, String unit) {
        this.textView = textView;
        this.child = child;
        this.unit = unit;
    }

    public void getData() {
        databaseReference.child(this.child).addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                textView.setText(snapshot.getValue().toString() + unit);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                textView.setText("No Data");
            }
        });
    }

    public void getDataFromChild() {
        databaseReference.child(this.child).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                data = snapshot.getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                data = "";
            }
        });
    }


    public void getDataDec1() {
        final DecimalFormat format = new DecimalFormat("#.0");
        databaseReference.child(this.child).addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String t = snapshot.getValue().toString();
                float temp = Float.parseFloat(t);
                if (temp > -0.5 && temp < 0.5) {
                    textView.setText("0" + unit);
                } else if (temp < 1 && temp >= 0.5 || temp > -1 && temp <= 0.5) {
                    String tempera = format.format(temp);
                    textView.setText("0" + tempera + unit);
                } else {
                    String tempera = format.format(temp);
                    textView.setText(tempera + unit);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                textView.setText("No Data");
            }
        });
    }

    public void getString() {
        databaseReference.child(this.child).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                textView.setText(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                textView.setText("No Data");
            }
        });
    }

    public void getTimer() {
        databaseReference.child(this.child).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Hour = snapshot.child("Hour").getValue().toString();
                Minute = snapshot.child("Minute").getValue().toString();
                Second = snapshot.child("Second").getValue().toString();
                Timer = Hour + " : " + Minute + " : " + Second;
                textView.setText(Timer);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
