package com.example.bluetooth.initbluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket

abstract class BaseBluetooth {

    /* variable */
    abstract var bluetoothAdapter: BluetoothAdapter
    abstract var bluetoothSocket: BluetoothSocket?
    /* ******** */


    /* function */
    abstract fun isBluetoothEnable():Boolean

    abstract fun getListDevices(): MutableList<BluetoothDevice>

    abstract fun onStartConnect(bluetoothDevice: BluetoothDevice)

    abstract fun onSendData(data: Any)

    abstract suspend fun onReceived()

    abstract fun onCloseSocket()

    /* fun check */
    abstract fun checkSocketStatus(isSocketConnected: Boolean)

    abstract fun showData(data: Any)
    /* ******** */

}