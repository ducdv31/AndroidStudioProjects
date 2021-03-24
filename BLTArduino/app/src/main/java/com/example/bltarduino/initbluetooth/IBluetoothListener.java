package com.example.bltarduino.initbluetooth;

public interface IBluetoothListener {
    void onBTSocketChange(boolean status);
    void onDataReceived(Object object);
}
