package com.example.bltarduino;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.bltarduino.initbluetooth.InitBluetooth;
import com.example.bltarduino.recyclerview.RecyclerDevicesView;

public class DevicesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private InitBluetooth initBluetooth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices);
        recyclerView = findViewById(R.id.recyclerView);
        RecyclerDevicesView recyclerDevicesView = new RecyclerDevicesView(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                RecyclerView.VERTICAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerDevicesView);

        initBluetooth = new InitBluetooth(this);
        initBluetooth.init();
        recyclerDevicesView.setData(initBluetooth.getPairedDevices());

    }

}