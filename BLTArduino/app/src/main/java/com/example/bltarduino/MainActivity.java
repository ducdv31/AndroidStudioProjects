package com.example.bltarduino;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bltarduino.initbluetooth.IBluetoothListener;
import com.example.bltarduino.initbluetooth.InitBluetooth;
import com.example.bltarduino.initbluetooth.ThreadReceiver;
import com.example.bltarduino.recyclerview.RecyclerDevicesView;

import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import java.util.logging.LogRecord;

import static com.example.bltarduino.initbluetooth.ThreadReceiver.DATA_RECEIVER;

public class MainActivity extends AppCompatActivity implements IBluetoothListener {

    private Button send, sendOff, Connect;
    private TextView stt;
    private InitBluetooth initBluetooth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        send = findViewById(R.id.send);
        sendOff = findViewById(R.id.sendoff);
        Connect = findViewById(R.id.connect);
        stt = findViewById(R.id.socketStt);
        RecyclerView recyclerView = findViewById(R.id.rcv_main);
        RecyclerDevicesView recyclerDevicesView = new RecyclerDevicesView(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                RecyclerView.VERTICAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerDevicesView);

        initBluetooth = new InitBluetooth(this);
        initBluetooth.init();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initBluetooth.sendData(49);
            }
        });
        sendOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initBluetooth.sendData(48);
            }
        });

        Connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerDevicesView.setData(initBluetooth.getPairedDevices());
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        initBluetooth.closeBLTSocket();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initBluetooth.init();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initBluetooth.init();
    }

    @Override
    public void onBTSocketChange(boolean status) {
        if (status) {
            stt.setText("Connected");
        } else {
            stt.setText("Disconnected");
        }
    }

    @Override
    public void onDataReceived(Object object) {
        stt.setText(object.toString().trim());
    }
}