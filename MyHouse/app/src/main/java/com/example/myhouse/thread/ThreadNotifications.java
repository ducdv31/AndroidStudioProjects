package com.example.myhouse.thread;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.Toast;

public class ThreadNotifications extends Thread {
    private final Context context;
    public ThreadNotifications(Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        if (this.isAlive()){
            Toast.makeText(context, "Thread Notification running", Toast.LENGTH_SHORT).show();
        }
    }

}
