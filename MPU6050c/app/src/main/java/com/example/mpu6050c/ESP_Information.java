package com.example.mpu6050c;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Objects;

public class ESP_Information extends AppCompatActivity {
    private TextView ESP_Wifi_Name, ESP_IP, Gateway_IP, Subnet_Mask,ESP_Time;
    @Override
    public  boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_s_p__information);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("ESP32");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ESP_Wifi_Name = (TextView) findViewById(R.id.wifi_infor);
        ESP_IP = (TextView) findViewById(R.id.esp_ip_info);
        Gateway_IP = (TextView) findViewById(R.id.gateway_ip_infor);
        Subnet_Mask = (TextView) findViewById(R.id.subnet_mask_infor);
        ESP_Time = findViewById(R.id.ESP_Time);
        // ESP Information
        getDatabase ESP_Wifi = new getDatabase(ESP_Wifi_Name, "ESP Information/WiFi", "");
        getDatabase ESP_ip = new getDatabase(ESP_IP, "ESP Information/IP", "");
        getDatabase Gateway = new getDatabase(Gateway_IP, "ESP Information/Gateway IP", "");
        getDatabase Subnet = new getDatabase(Subnet_Mask, "ESP Information/Subnet mask", "");
        ESP_Wifi.getString();
        ESP_ip.getString();
        Gateway.getString();
        Subnet.getString();
        // ESP Timer
        getDatabase Sec = new getDatabase(ESP_Time, "ESP Timer/Time", "");
        Sec.getTimer();
    }
}