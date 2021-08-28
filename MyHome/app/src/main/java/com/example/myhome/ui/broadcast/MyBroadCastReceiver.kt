package com.example.myhome.ui.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class MyBroadCastReceiver : BroadcastReceiver() {

    private val TAG = MyBroadCastReceiver::class.java.simpleName
    /* BluetoothAdapter.ACTION_STATE_CHANGED == intent?.action */

    override fun onReceive(context: Context?, intent: Intent?) {

    }
}