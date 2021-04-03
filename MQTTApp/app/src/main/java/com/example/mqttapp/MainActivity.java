package com.example.mqttapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mqttapp.mqtt.MyMqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button pub = findViewById(R.id.publish);
        Button sub = findViewById(R.id.subscribe);
        Button connect = findViewById(R.id.connect);
        Button unsub = findViewById(R.id.unsubscribe);
        Button disconnect = findViewById(R.id.disconnect);
        MyMqtt myMqtt = new MyMqtt(this, new MyMqtt.MyMqttListener() {
            @Override
            public void ServerConnected(boolean status) {
                if (status) {
                    Toast.makeText(MainActivity.this, "Connected", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Disconnected", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void MessageArrived(String topic, MqttMessage mqttMessage) {
                Toast.makeText(MainActivity.this, mqttMessage.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void DeliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }

            @Override
            public void NotifyConnected(boolean notify) {
                Toast.makeText(MainActivity.this, "Please connect first", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void OnSubscribe(boolean subscribe) {
                if (subscribe){
                    Toast.makeText(MainActivity.this, "Subscribed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Can't subscribe", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void OnUnsubscribe(boolean unsubscribe) {
                if (unsubscribe) {
                    Toast.makeText(MainActivity.this, "UnSubscribe", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Not Unsubscribe", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void OnDisconnect(boolean disconnected) {
                if (disconnected){
                    Toast.makeText(MainActivity.this, "Disconnected", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Can't disconnected", Toast.LENGTH_SHORT).show();
                }
            }
        });
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myMqtt.connectServerUri("tcp://test.mosquitto.org", 1883);
            }
        });
        pub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myMqtt.publish("esp32", "Hello world", false);
            }
        });
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myMqtt.subscribe("esp32", 0);
            }
        });
        unsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myMqtt.unSubscribe("esp32");
            }
        });
        disconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myMqtt.disconnect();
            }
        });
    }

}