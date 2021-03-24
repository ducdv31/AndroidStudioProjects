package com.example.btarduino.broadcast;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.btarduino.initbluetooth.IBluetoothListener;

public class MyBroadcastReceiver extends BroadcastReceiver {

    private final IBluetoothListener iBluetoothListener;

    public MyBroadcastReceiver(Context context) {
        iBluetoothListener = (IBluetoothListener) context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
            final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                    BluetoothAdapter.ERROR);
            if (state == BluetoothAdapter.STATE_OFF) {
                iBluetoothListener.onBTSocketChange(false);
            }
        }
    }
}
