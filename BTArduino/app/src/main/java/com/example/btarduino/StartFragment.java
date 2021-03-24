package com.example.btarduino;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.btarduino.initbluetooth.IBluetoothListener;

public class StartFragment extends Fragment {

    private MainActivity mainActivity;
    private EditText editText;

    public StartFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View StartView = inflater.inflate(R.layout.fragment_start, container, false);
        mainActivity = (MainActivity) getActivity();
        Button on = StartView.findViewById(R.id.on_btn);
        Button off = StartView.findViewById(R.id.off_btn);
        Button devices = StartView.findViewById(R.id.device_list);
        Button sendString = StartView.findViewById(R.id.send_string);
        editText = StartView.findViewById(R.id.editText);
        on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.send(49);
            }
        });

        off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.send(48);
            }
        });

        devices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.openDevicesFragment();
            }
        });
        sendString.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.sendString(editText.getText().toString());
                editText.setText("");
            }
        });
        return StartView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}