package com.example.myhouse.NavFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myhouse.DataDatabase.GetDatabase;
import com.example.myhouse.R;

public class FragmentBoardInfor extends Fragment {

    public FragmentBoardInfor() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View BoardView = inflater.inflate(R.layout.fragment_board_infor, container, false);
        TextView ESP_Wifi_Name = (TextView) BoardView.findViewById(R.id.wifi_infor);
        TextView ESP_IP = (TextView) BoardView.findViewById(R.id.esp_ip_info);
        TextView gateway_IP = (TextView) BoardView.findViewById(R.id.gateway_ip_infor);
        TextView subnet_Mask = (TextView) BoardView.findViewById(R.id.subnet_mask_infor);
        GetDatabase ESP_Wifi = new GetDatabase(ESP_Wifi_Name, "ESP Information/WiFi", "");
        GetDatabase ESP_ip = new GetDatabase(ESP_IP, "ESP Information/IP", "");
        GetDatabase Gateway = new GetDatabase(gateway_IP, "ESP Information/Gateway IP", "");
        GetDatabase Subnet = new GetDatabase(subnet_Mask, "ESP Information/Subnet mask", "");
        ESP_Wifi.getString();
        ESP_ip.getString();
        Gateway.getString();
        Subnet.getString();
        return BoardView;
    }
}