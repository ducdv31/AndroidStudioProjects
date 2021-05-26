package com.example.bluetooth.initbluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.util.Log
import java.io.IOException
import java.io.InputStream
import java.util.*

abstract class BaseBluetooth {

    /* variable */
    abstract var bluetoothAdapter: BluetoothAdapter
    abstract var bluetoothSocket: BluetoothSocket?
    abstract var bluetoothDevice: BluetoothDevice?
    /* ******** */


    /* function */
    abstract fun getListDevices(): MutableList<BluetoothDevice>

    abstract fun onStartConnect(bluetoothDevice: BluetoothDevice)

    abstract suspend fun onSendData(data: Any)

    abstract suspend fun onReceived()

    abstract fun onCloseSocket()
    /* ******** */

}