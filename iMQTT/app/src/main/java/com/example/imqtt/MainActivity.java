package com.example.imqtt;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.imqtt.mqttconfig.MyMqtt;
import com.example.imqtt.navigation.tabinmain.ConfigMqttFragment;
import com.example.imqtt.navigation.tabinmain.SubscribeFragment;
import com.example.imqtt.navigation.tabinmain.rcv_adapter.DataModel;
import com.example.imqtt.sharedpreference.DataLocalManager;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private MyMqtt myMqtt;
    private List<DataModel> listDataSub;
    private long backPressedTime;
    private Toast mToast;
    private MenuItem connect_menu;

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
                R.id.nav_about)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        initMQTT();
        listDataSub = new ArrayList<>();
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                closeKeyboard();
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                closeKeyboard();
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        String Port = DataLocalManager.getPortMQTT();
        String Host = DataLocalManager.getHostMQTT();
        String Username = DataLocalManager.getUsernameMQTT();
        String Password = DataLocalManager.getPasswordMQTT();
        if (!Host.isEmpty() && !Port.isEmpty() && Username.isEmpty() && Password.isEmpty()) {
            connect_mqtt(Host,
                    Integer.parseInt(Port));
            closeKeyboard();
        } else if (!Host.isEmpty() && !Port.isEmpty() && !Username.isEmpty() && !Password.isEmpty()) {
            connect_with_user_mqtt(Host,
                    Integer.parseInt(Port),
                    Username,
                    Password);
            closeKeyboard();
        } else {
            Toast.makeText(this, "Please check host or port", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disconnect_mqtt();
    }

    private void initMQTT() {
        myMqtt = new MyMqtt(this, new MyMqtt.MyMqttListener() {
            @Override
            public void ServerConnected(boolean status) {
                if (status) {
                    Toast.makeText(MainActivity.this, "Connected", Toast.LENGTH_SHORT).show();
                    connect_menu.setIcon(R.drawable.icons8_internet_of_things_green_50);
                } else {
                    Toast.makeText(MainActivity.this, "Disconnected", Toast.LENGTH_SHORT).show();
                    connect_menu.setIcon(R.drawable.icons8_internet_of_things_50);
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
                listDataSub.add(new DataModel(getTime(), topic, mqttMessage.toString()));
                SubscribeFragment.subDataAdapter.setData(listDataSub);
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

    private String getTime() {
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

        Date currentTime1 = Calendar.getInstance().getTime();
        return currentTime + "   ||   " + currentDate;
    }

    public void connect_mqtt(String host, int port) {
        myMqtt.connectServerUri(host, port);
    }

    public void connect_with_user_mqtt(String host, int port, String username, String password) {
        myMqtt.connectServerUriSecure(host, port, username, password);
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

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mqtt_config:
                String Port = DataLocalManager.getPortMQTT();
                String Host = DataLocalManager.getHostMQTT();
                if (!Host.isEmpty() && !Port.isEmpty()) {
                    connect_mqtt(Host, Integer.parseInt(Port));
                }

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        connect_menu = menu.findItem(R.id.mqtt_config);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            mToast.cancel();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            finish();
            return;
        } else {
            mToast = Toast.makeText(this, "Press Back again to Exit", Toast.LENGTH_SHORT);
            mToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }

    public void closeKeyboard() {
        // this will give us the view
        // which is currently focus
        // in this layout
        View view = this.getCurrentFocus();

        // if nothing is currently
        // focus then this will protect
        // the app from crash
        if (view != null) {

            // now assign the system
            // service to InputMethodManager
            InputMethodManager manager
                    = (InputMethodManager) this
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}