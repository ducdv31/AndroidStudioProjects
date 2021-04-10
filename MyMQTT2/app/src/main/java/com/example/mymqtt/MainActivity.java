package com.example.mymqtt;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mymqtt.mqttconfig.MyMqtt;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    private MyMqtt myMqtt;
    private String Host;
    private String Port;
    private String Username;
    private String Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,
                R.id.nav_mqtt_config,
                R.id.nav_led_control)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        initMQTT();


    }

    private void initMQTT() {
        myMqtt = new MyMqtt(this, new MyMqtt.MyMqttListener() {
            @Override
            public void ServerConnected(boolean status) {
                if (status) {
                    Toast.makeText(MainActivity.this, "Connected", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Disconnected", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void NotifyConnected(boolean notify) {

            }

            @Override
            public void OnSubscribe(boolean subscribe) {
                if (subscribe)
                    Toast.makeText(MainActivity.this, "Subscribed", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Can't Subscribe", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void MessageArrived(String topic, MqttMessage mqttMessage) {
                Toast.makeText(MainActivity.this, topic + " - " + mqttMessage.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void DeliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }

            @Override
            public void OnUnsubscribe(boolean unsubscribe) {
                if (unsubscribe) {
                    Toast.makeText(MainActivity.this, "Unsubscribe", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Not Unsubscribe yet", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void OnDisconnect(boolean disconnected) {

            }
        });
    }

    public void set_config_mqtt(String host, String port, String username, String password) {
        Host = host;
        Port = port; // null exeptions
        Username = username;
        Password = password;
//        myMqtt.connectServerUri(Host, Integer.parseInt(Port));
    }

    public void connect_mqtt(String host, int port) {
        if (Host == null) {
            Host = "tcp://test.mosquitto.org";
        }
        if (Port == null) {
            Port = "1883";
        }
        myMqtt.connectServerUri(Host, Integer.parseInt(Port));
    }

    public void subscribe_topic(String topic, int QoS) {
        myMqtt.subscribe(topic, QoS);
    }

    public void unsubscribe_topic(String topic) {
        myMqtt.unSubscribe(topic);
    }

    public void publish(String topic, String content, boolean remain) {
        myMqtt.publish(topic, content, remain);
    }

    public void disconnect_mqtt() {
        myMqtt.disconnect();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.connect_mqtt:
                connect_mqtt(null, 0);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}