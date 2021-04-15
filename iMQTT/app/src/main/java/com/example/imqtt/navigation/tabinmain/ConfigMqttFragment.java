package com.example.imqtt.navigation.tabinmain;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.imqtt.MainActivity;
import com.example.imqtt.R;
import com.example.imqtt.sharedpreference.DataLocalManager;

public class ConfigMqttFragment extends Fragment {

    private EditText host;
    private EditText port;
    private EditText username;
    private EditText password;
    private MainActivity mainActivity;

    public ConfigMqttFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View configView = inflater.inflate(R.layout.fragment_config_mqtt, container, false);
        mainActivity = (MainActivity) getActivity();
        host = configView.findViewById(R.id.host_mqtt);
        port = configView.findViewById(R.id.port_mqtt);
        username = configView.findViewById(R.id.username_mqtt);
        password = configView.findViewById(R.id.password_mqtt);
        Button connect_mqtt_server = configView.findViewById(R.id.connect_mqtt);
        connect_mqtt_server.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Host = host.getText().toString();
                String Port = port.getText().toString();
                if (!Host.isEmpty() && !Port.isEmpty()) {
                    mainActivity.connect_mqtt(host.getText().toString(),
                            Integer.parseInt(port.getText().toString()));
                    closeKeyboard();
                } else {
                    Toast.makeText(mainActivity, "Please check host or port", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return configView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        restoreConfigMQTT();
    }

    @Override
    public void onPause() {
        super.onPause();
        DataLocalManager.setHostMQTT(host.getText().toString());
        DataLocalManager.setPortMQTT(port.getText().toString());
        DataLocalManager.setUsernameMQTT(username.getText().toString());
        DataLocalManager.setPasswordMQTT(password.getText().toString());
        closeKeyboard();
    }

    @Override
    public void onResume() {
        super.onResume();
        restoreConfigMQTT();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        restoreConfigMQTT();
    }
    private void restoreConfigMQTT(){
        host.setText(DataLocalManager.getHostMQTT());
        port.setText(DataLocalManager.getPortMQTT());
        username.setText(DataLocalManager.getUsernameMQTT());
        password.setText(DataLocalManager.getPasswordMQTT());
    }
    private void closeKeyboard() {
        // this will give us the view
        // which is currently focus
        // in this layout
        View view = requireActivity().getCurrentFocus();

        // if nothing is currently
        // focus then this will protect
        // the app from crash
        if (view != null) {

            // now assign the system
            // service to InputMethodManager
            InputMethodManager manager
                    = (InputMethodManager) requireActivity()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}