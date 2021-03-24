package com.example.btarduino.initbluetooth;

import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.OutputStream;

public class ThreadSendBt {

    private final BluetoothSocket bluetoothSocket;

    public ThreadSendBt(BluetoothSocket bluetoothSocket) {
        this.bluetoothSocket = bluetoothSocket;
        if (!bluetoothSocket.isConnected()) {
            try {
                bluetoothSocket.connect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendDataBT(int data) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OutputStream outputStream = bluetoothSocket.getOutputStream();
                    outputStream.write(data);
                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public  void sendStringBT(String string){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] data = string.getBytes();
                try {
                    OutputStream outputStream = bluetoothSocket.getOutputStream();
                    outputStream.write(data);
                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        thread.start();
    }

}
