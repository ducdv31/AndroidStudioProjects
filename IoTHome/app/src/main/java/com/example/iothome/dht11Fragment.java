package com.example.iothome;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class dht11Fragment extends Fragment {
    private TextView Temp, Humi;

    public dht11Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View dht11View = inflater.inflate(R.layout.fragment_dht11,
                container,
                false);
        Temp = (TextView) dht11View.findViewById(R.id.temperature);
        Humi = (TextView) dht11View.findViewById(R.id.humidity);
        // Inflate the layout for this fragment
//        String Temperature = getArguments().getString("Temp");
//        Temp.setText(Temperature);
        GetDatabase temp = new GetDatabase(Temp, "DHT11/t", " ºC");
        GetDatabase hum = new GetDatabase(Humi, "DHT11/h", "  % ");
        temp.getDataDec1();
        hum.getData();
        return dht11View;
    }
}