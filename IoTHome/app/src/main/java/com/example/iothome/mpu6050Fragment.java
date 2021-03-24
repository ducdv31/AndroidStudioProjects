package com.example.iothome;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class mpu6050Fragment extends Fragment {
    private TextView acx, acy, acz, gyx, gyy, gyz;

    public mpu6050Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View mpu6050View = inflater.inflate(R.layout.fragment_mpu6050,
                container,
                false);
        // Inflate the layout for this fragment
        acx = (TextView) mpu6050View.findViewById(R.id.acx);
        acy = (TextView) mpu6050View.findViewById(R.id.acy);
        acz = (TextView) mpu6050View.findViewById(R.id.acz);
        gyx = (TextView) mpu6050View.findViewById(R.id.gyx);
        gyy = (TextView) mpu6050View.findViewById(R.id.gyy);
        gyz = (TextView) mpu6050View.findViewById(R.id.gyz);
        GetDatabase AcX = new GetDatabase(acx, "MPU6050/AcX", " º");
        GetDatabase AcY = new GetDatabase(acy, "MPU6050/AcY", " º");
        GetDatabase AcZ = new GetDatabase(acz, "MPU6050/AcZ", " º");
        GetDatabase GyX = new GetDatabase(gyx, "MPU6050/GyX", " º/s");
        GetDatabase GyY = new GetDatabase(gyy, "MPU6050/GyY", " º/s");
        GetDatabase GyZ = new GetDatabase(gyz, "MPU6050/GyZ", " º/s");
        AcX.getData();
        AcY.getData();
        AcZ.getData();
        GyX.getData();
        GyY.getData();
        GyZ.getData();


        return mpu6050View;
    }
}