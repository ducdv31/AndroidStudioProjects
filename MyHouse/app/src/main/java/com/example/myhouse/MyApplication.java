package com.example.myhouse;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.myhouse.DataLocal.DataLocalManager;
import com.example.myhouse.notification.MyNotifications;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.List;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DataLocalManager.init(getApplicationContext());
        MyNotifications myNotifications = new MyNotifications(getApplicationContext());
        myNotifications.createNotificationChannels();
    }

    public static void checkPermission(Context context) {
        TedPermission.with(context)
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        Toast.makeText(context, "Granted", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionDenied(List<String> deniedPermissions) {
                        Toast.makeText(context, "Denied", Toast.LENGTH_SHORT).show();
                    }
                })
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .setDeniedTitle("Open setting permission")
                .setDeniedMessage("Open Setting - > Permission")
                .check();
    }

    public static void checkNet(TextView Status) {
        DatabaseReference connectedRef = FirebaseDatabase
                .getInstance()
                .getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    Status.setTextColor(Color.GREEN);
                } else {
                    Status.setTextColor(Color.RED);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Status.setTextColor(Color.RED);
            }
        });
    }
}
