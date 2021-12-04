package com.example.custombroadcast

import android.content.BroadcastReceiver
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log

class MyBroadcast : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            MainActivity.MY_ACTION -> {
                Log.e(TAG, "onReceive: ${intent.getStringExtra(MainActivity.MY_DATA)}")
            }
        }
    }
}