package com.example.adccenter.navfragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.adccenter.DataDatabase.GetDatabase;
import com.example.adccenter.R;

public class FragmentEsp32 extends Fragment {
    private TextView ESP_Wifi_Name, ESP_IP, Gateway_IP, Subnet_Mask, ESP_Time;

    public FragmentEsp32() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View espView = inflater.inflate(R.layout.fragment_esp32, container, false);
        ESP_Wifi_Name = (TextView) espView.findViewById(R.id.wifi_infor);
        ESP_IP = (TextView) espView.findViewById(R.id.esp_ip_info);
        Gateway_IP = (TextView) espView.findViewById(R.id.gateway_ip_infor);
        Subnet_Mask = (TextView) espView.findViewById(R.id.subnet_mask_infor);
        ESP_Time = espView.findViewById(R.id.ESP_Time);
        GetDatabase ESP_Wifi = new GetDatabase(ESP_Wifi_Name, "ESP Information/WiFi", "");
        GetDatabase ESP_ip = new GetDatabase(ESP_IP, "ESP Information/IP", "");
        GetDatabase Gateway = new GetDatabase(Gateway_IP, "ESP Information/Gateway IP", "");
        GetDatabase Subnet = new GetDatabase(Subnet_Mask, "ESP Information/Subnet mask", "");
        ESP_Wifi.getString();
        ESP_ip.getString();
        Gateway.getString();
        Subnet.getString();
        GetDatabase Sec = new GetDatabase(ESP_Time, "ESP Timer/Time", "");
        Sec.getTimer();
        return espView;
    }
}