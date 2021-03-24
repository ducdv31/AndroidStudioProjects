package com.example.bltarduino.initbluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class InitBluetooth {

    public String BTMac = "F0:08:D1:D7:57:4A";
    IBluetoothListener iBluetoothListener;
    protected BluetoothAdapter bluetoothAdapter;
    protected BluetoothDevice bluetoothDevice;
    protected BluetoothSocket bluetoothSocket;
    protected UUID mUUID;
    protected ThreadReceiver threadReceiver;
    protected ThreadSendBt threadSendBt;
    private final Context context;

    public InitBluetooth(Context context) {
        this.context = context;
        iBluetoothListener = (IBluetoothListener) context;
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public void init() {
        getPairedDevices();
        initBT(BTMac);
        startConnection();
    }

    public List<BluetoothDevice> getPairedDevices() {
        Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();
//        Log.e("List", Arrays.toString(new ArrayList<>(devices).toArray()));
        return new ArrayList<>(devices);
    }

    public void startConnection() {
        try {
            bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(mUUID);
            bluetoothSocket.connect();
            iBluetoothListener.onBTSocketChange(BTSocketConnected());
        } catch (IOException e) {
            e.printStackTrace();
        }
        threadReceiver = new ThreadReceiver(context, bluetoothSocket);
        threadReceiver.start();
        threadSendBt = new ThreadSendBt(bluetoothSocket);
    }

    public void initBT(String mac) {
        bluetoothDevice = bluetoothAdapter.getRemoteDevice(mac);
        // Get UUID
        String uuid = bluetoothDevice.getUuids()[0].toString();
        mUUID = UUID.fromString(uuid);
    }

    public void closeBLTSocket() {
        try {
            bluetoothSocket.close();
            iBluetoothListener.onBTSocketChange(BTSocketConnected());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendData(int data) {
        threadSendBt.sendDataBT(data);
    }

    public boolean BTSocketConnected() {
        return bluetoothSocket.isConnected();
    }

}
