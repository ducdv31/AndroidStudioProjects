package com.example.btarduino.initbluetooth;

import android.bluetooth.BluetoothDevice;

public interface IBluetoothListener {
    void onBTSocketChange(boolean status);
    void onDataReceived(Object object);
    void SelectedDevice(BluetoothDevice bluetoothDevice);
}
