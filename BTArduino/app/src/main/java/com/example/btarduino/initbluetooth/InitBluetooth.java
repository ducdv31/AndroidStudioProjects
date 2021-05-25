package com.example.btarduino.initbluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class InitBluetooth {

    private String BTMac;
    private final IBluetoothListener iBluetoothListener;
    protected BluetoothAdapter bluetoothAdapter;
    protected BluetoothDevice bluetoothDevice;
    protected BluetoothSocket bluetoothSocket;
    protected UUID mUUID;
    protected ThreadReceiver threadReceiver;
    protected ThreadSendBt threadSendBt;
    private final Context context;

    public InitBluetooth(Context context) {
        this.context = context;
        BTMac = null;
        iBluetoothListener = (IBluetoothListener) context;
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothDevice = null;
        bluetoothSocket = null;
    }

    public void init(String MacAddress) {
        // when select device ->get mac device
//        getPairedDevices();
        setBTMac(MacAddress);
        initBT(MacAddress);
        startConnection();
    }

    public List<BluetoothDevice> getPairedDevices() {
        Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();
        return new ArrayList<>(devices);
    }

    public void initBT(String mac) {
        bluetoothDevice = bluetoothAdapter.getRemoteDevice(mac);
        // Get UUID
        String uuid = bluetoothDevice.getUuids()[0].toString();
        mUUID = UUID.fromString(uuid);
    }

    public void startConnection() {
        try {
            bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(mUUID);
            bluetoothSocket.connect();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            iBluetoothListener.onBTSocketChange(BTSocketConnected());
        }
        if (!BTSocketConnected()) {
            bluetoothDevice = null;
            setBTMac(null); // <-
        } else {
            threadReceiver = new ThreadReceiver(context, bluetoothSocket);
            threadReceiver.start();
            threadSendBt = new ThreadSendBt(bluetoothSocket);
        }
    }

    public void closeBLTSocket() {
        if (bluetoothDevice != null) {
            try {
                bluetoothSocket.close();
                iBluetoothListener.onBTSocketChange(BTSocketConnected());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                bluetoothDevice = null;
            }
        }
    }

    public void sendData(int data) {
        if (bluetoothDevice != null) {
            threadSendBt.sendDataBT(data);
        } else {
            Toast.makeText(context, "No device connected", Toast.LENGTH_SHORT).show();
        }
    }

    public void sendStringData(String string) {
        if (bluetoothDevice != null) {
            threadSendBt.sendStringBT(string);
        } else {
            Toast.makeText(context, "No device connected", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean checkBTAdapterIsEnable() {
        return bluetoothAdapter.isEnabled();
    }

    public void turnOnBT(){
        bluetoothAdapter.enable();
    }

    public void turnOffBT(){
        bluetoothAdapter.disable();
    }

    public boolean BTSocketConnected() {
        return bluetoothSocket.isConnected();
    }

    public String getBTMac() {
        return BTMac;
    }

    public void setBTMac(String BTMac) {
        this.BTMac = BTMac;
    }

    public BluetoothDevice getBluetoothDevice() {
        return bluetoothDevice;
    }

    public BluetoothSocket getBluetoothSocket() {
        return bluetoothSocket;
    }
}
