package com.example.mymqtt.navfragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.mymqtt.MainActivity;
import com.example.mymqtt.R;
import com.example.mymqtt.sharedpreference.DataLocalManager;

public class FragmentConfigMqtt extends Fragment {
    private EditText host;
    private EditText port;
    private EditText username;
    private EditText password;
    private Button connect_mqtt_server;
    private Button save_mqtt_config;
    private View configMqttView;
    private MainActivity mainActivity;

    public FragmentConfigMqtt() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        configMqttView = inflater.inflate(R.layout.fragment_config_mqtt, container, false);
        mainActivity = (MainActivity) getActivity();
        initVariable();
        initButton();



        return configMqttView;
    }

    @Override
    public void onPause() {
        super.onPause();
        DataLocalManager.setHostMQTT(host.getText().toString());
        DataLocalManager.setPortMQTT(port.getText().toString());
        DataLocalManager.setUsernameMQTT(username.getText().toString());
        DataLocalManager.setPasswordMQTT(password.getText().toString());
    }

    @Override
    public void onResume() {
        super.onResume();
        restoreData();
    }

    @Override
    public void onStart() {
        super.onStart();
        restoreData();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        restoreData();
    }

    private void restoreData() {
        host.setText(DataLocalManager.getHostMQTT());
        port.setText(DataLocalManager.getPortMQTT());
        username.setText(DataLocalManager.getUsernameMQTT());
        password.setText(DataLocalManager.getPasswordMQTT());
    }

    private void initButton() {
        connect_mqtt_server.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.set_config_mqtt(host.getText().toString(),
                        port.getText().toString(),
                        username.getText().toString(),
                        password.getText().toString());
                mainActivity.connect_mqtt(host.getText().toString(),
                        Integer.parseInt(port.getText().toString()));
            }
        });
        save_mqtt_config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String portTemp = "";
                if (port.getText() == null) {
                    portTemp = "1883";
                }
                mainActivity.set_config_mqtt(host.getText().toString(),
                        portTemp,
                        username.getText().toString(),
                        password.getText().toString());
            }
        });
    }


    void initVariable() {
        host = configMqttView.findViewById(R.id.host_mqtt);
        port = configMqttView.findViewById(R.id.port_mqtt);
        username = configMqttView.findViewById(R.id.username_mqtt);
        password = configMqttView.findViewById(R.id.password_mqtt);
        connect_mqtt_server = configMqttView.findViewById(R.id.connect_mqtt);
        save_mqtt_config = configMqttView.findViewById(R.id.save_mqtt_config);
    }

}