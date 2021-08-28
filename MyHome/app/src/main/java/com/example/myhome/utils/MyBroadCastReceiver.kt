package com.example.myhome.utils

import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.util.Log

class MyBroadCastReceiver : BroadcastReceiver() {

    private val TAG = MyBroadCastReceiver::class.java.simpleName
    /* BluetoothAdapter.ACTION_STATE_CHANGED == intent?.action */

    override fun onReceive(context: Context?, intent: Intent?) {

    }
}