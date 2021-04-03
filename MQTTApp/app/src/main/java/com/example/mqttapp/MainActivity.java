package com.example.mqttapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectServerUri("tcp://test.mosquitto.org:1883");
    }

    public void connectServerUri(String uri){
        String clientId = MqttClient.generateClientId();
        MqttAndroidClient client =
                new MqttAndroidClient(this.getApplicationContext(), uri,
                        clientId);
        try {
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Log.e("TAG", "onSuccess");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Log.e("TAG", "onFailure");

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }

        /* Connect MQTT version 3.1 */
        MqttConnectOptions options = new MqttConnectOptions();
        options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
        try {
            IMqttToken token = client.connect(options);
        } catch (MqttException e) {
            e.printStackTrace();
        }

        /* Publish */
//        String topic = "esp32";
//        String payload = "the payload";
//        byte[] encodedPayload = new byte[2];
//        encodedPayload[0] = 'H';
//        encodedPayload[1] = 'i';
//        try {
//            encodedPayload = payload.getBytes(StandardCharsets.UTF_8);
//            MqttMessage message = new MqttMessage(encodedPayload);
//            client.publish(topic, message);
//        } catch (MqttException e) {
//            e.printStackTrace();
//        }

    }
}