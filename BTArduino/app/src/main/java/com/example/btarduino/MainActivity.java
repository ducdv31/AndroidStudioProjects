package com.example.btarduino;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.btarduino.broadcast.MyBroadcastReceiver;
import com.example.btarduino.initbluetooth.IBluetoothListener;
import com.example.btarduino.initbluetooth.InitBluetooth;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements IBluetoothListener {

    private static final String TAG_START_FRAG = "Start fragment";
    private static final String TAG_DEVICES_FRAG = "Devices fragment";
    private static final int REQUIRE_ENABLE_BT = 1;
    private InitBluetooth initBluetooth;
    private String BTMac = null;
    private MyBroadcastReceiver myBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, new StartFragment(), TAG_START_FRAG);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        initBluetooth = new InitBluetooth(this);
        myBroadcastReceiver = new MyBroadcastReceiver(this);
    }

    public void send(int data) {
        initBluetooth.sendData(data);
    }

    public void sendString(String string) {
        initBluetooth.sendStringData(string);
    }

    public void openDevicesFragment() {
        if (initBluetooth.checkBTAdapterIsEnable()) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, new DevicesFragment(), TAG_DEVICES_FRAG);
            fragmentTransaction.addToBackStack(DevicesFragment.PBS_DEVICES);
            fragmentTransaction.commit();
        } else {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, REQUIRE_ENABLE_BT);
        }
    }

    public ArrayList<BluetoothDevice> getListDevice() {
        return (ArrayList<BluetoothDevice>) initBluetooth.getPairedDevices();
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(myBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(myBroadcastReceiver);
    }

    public void removeDevicesFragment() {
        DevicesFragment devicesFragment = (DevicesFragment) getSupportFragmentManager()
                .findFragmentByTag(TAG_DEVICES_FRAG);
        getSupportFragmentManager().beginTransaction().remove(new DevicesFragment()).commit();
        Toast.makeText(this, "Delete Devices Fragment", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        StartFragment startFragment = (StartFragment) getSupportFragmentManager()
                .findFragmentByTag(TAG_START_FRAG);
        if (startFragment != null && startFragment.isVisible()) {
            this.finish();
        }
    }

    @Override
    public void onBTSocketChange(boolean status) {
        if (status) {
            Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Disconnected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDataReceived(Object object) {
        Toast.makeText(this, object.toString().trim(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void SelectedDevice(BluetoothDevice bluetoothDevice) {
        BTMac = bluetoothDevice.toString();
        initBluetooth.init(bluetoothDevice.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bt_devices:
                Toast.makeText(this, "BT Devices", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case REQUIRE_ENABLE_BT:
                if (resultCode == RESULT_OK) {
                    Toast.makeText(this,
                            "Turned Bluetooth on",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this,
                            "Fail to turn on Bluetooth",
                            Toast.LENGTH_SHORT).show();
                }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}