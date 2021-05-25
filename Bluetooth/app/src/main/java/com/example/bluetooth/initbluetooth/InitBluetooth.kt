package com.example.bluetooth.initbluetooth

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.util.Log
import java.util.*

class InitBluetooth : BaseBluetooth(){

    override fun getListDevices(): MutableList<BluetoothDevice> {
        return super.getListDevices()
    }

    override fun getSocket(bluetoothDevice: BluetoothDevice): BluetoothSocket {
        return super.getSocket(bluetoothDevice)
    }

    override fun onStartConnect(bluetoothDevice: BluetoothDevice) {
        super.onStartConnect(bluetoothDevice)
    }

    override fun onSendData(data: Any) {
        super.onSendData(data)
    }

    override fun onReceived() {
        super.onReceived()
    }
}