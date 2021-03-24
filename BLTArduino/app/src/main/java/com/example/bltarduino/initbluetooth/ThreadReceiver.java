package com.example.bltarduino.initbluetooth;

import android.bluetooth.BluetoothSocket;
import android.content.Context;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ThreadReceiver extends Thread {

    public static final int DATA_RECEIVER = 1;
    private BluetoothSocket bluetoothSocket;
    private Handler handler;
    private IBluetoothListener iBluetoothListener;

    public ThreadReceiver(Context context, BluetoothSocket bluetoothSocket) {
        this.bluetoothSocket = bluetoothSocket;
        iBluetoothListener = (IBluetoothListener) context;
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case DATA_RECEIVER:
                        iBluetoothListener.onDataReceived(msg.obj);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        if (!bluetoothSocket.isConnected()) {
            try {
                bluetoothSocket.connect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        startReceiver();
    }

    public void startReceiver() {
        try {
            InputStream is = bluetoothSocket.getInputStream();

            while (bluetoothSocket.isConnected()) {

                int b;
                StringBuilder text = new StringBuilder();
                while ((b = is.read()) != 13) {
                    String B = Character.toString((char) b);
                    text.append(B);
                }

//                BufferedInputStream bufferedInputStream = new BufferedInputStream(is);
//                byte[] bytes = new byte[1024];
//                int length = bufferedInputStream.read(bytes);
//                String data = new String(bytes, 0, length);
//                Log.e("Data", data);

                Message message = new Message();
                message.what = DATA_RECEIVER;
                message.obj = text.toString();
                handler.sendMessage(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
