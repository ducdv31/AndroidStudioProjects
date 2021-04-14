package com.example.myhouse.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.myhouse.MainActivity;
import com.example.myhouse.R;

public class MyNotifications extends Service {
    private static final String TEMP_CHANNEL_ID = "Temp channel ID";
    private static final String HUMI_CHANNEL_ID = "Humi channel ID";
    private static final String FOREGROUND_CHANNEL_ID = "Foreground channel ID";
    private static final int TEMP_NOTIFICATION_ID = 0;
    private static final int HUMI_NOTIFICATION_ID = 1;
    private static final int PM1_NOTIFICATION_ID = 2;
    private static final int PM25_NOTIFICATION_ID = 3;
    private static final int PM10_NOTIFICATION_ID = 4;
    private static final int FOREGROUND_NOTIFICATION_ID = 2;
    public static final String TEMP_HIGH = "Channel 1";
    public static final String TEMP_LOW = "Channel 2";
    public static final String HUMI_HIGH = "Channel 3";
    public static final String HUMI_LOW = "Channel 4";
    public static final String PM1_HIGH = "Channel 5";
    public static final String PM25_HIGH = "Channel 6";
    public static final String PM10_HIGH = "Channel 7";
    public static final String TEMP_NORM = "Delete temperature alert";
    public static final String HUMI_NORM = "Delete humidity alert";
    public static final String PMS1_NORM = "Delete PM 1 alert";
    public static final String PMS25_NORM = "Delete PM 2.5 alert";
    public static final String PMS10_NORM = "Delete PM 10 alert";
    public static final String FOREGROUND_ID = "Channel 5";
    private static final String PMS_CHANNEL_ID = "PMS channel ID";
    private final Context context;

    public MyNotifications(Context context) {
        this.context = context;
    }

    public void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel temperature = new NotificationChannel(
                    TEMP_CHANNEL_ID,
                    "Temperature Alert",
                    NotificationManager.IMPORTANCE_HIGH
            );

            NotificationChannel humidity = new NotificationChannel(
                    HUMI_CHANNEL_ID,
                    "Humidity Alert",
                    NotificationManager.IMPORTANCE_HIGH
            );

            NotificationChannel pms7003 = new NotificationChannel(
                    PMS_CHANNEL_ID,
                    "PMS Alert",
                    NotificationManager.IMPORTANCE_HIGH
            );


            NotificationChannel foreground = new NotificationChannel(
                    FOREGROUND_CHANNEL_ID,
                    "Foreground Alert",
                    NotificationManager.IMPORTANCE_HIGH
            );

            temperature.setDescription("Temperature Alert");
            temperature.enableLights(true);
            temperature.setLightColor(Color.RED);
            temperature.enableVibration(true);
            humidity.setDescription("Humidity Alert");
            humidity.enableLights(true);
            humidity.setLightColor(Color.RED);
            pms7003.setDescription("PMS Alert");
            pms7003.enableLights(true);

            foreground.setDescription("Foreground notification");
            NotificationManager notificationManager
                    = context.getSystemService(NotificationManager.class);

            notificationManager.createNotificationChannel(temperature);
            notificationManager.createNotificationChannel(humidity);
            notificationManager.createNotificationChannel(foreground);
            notificationManager.createNotificationChannel(pms7003);
        }
    }

    public void createNotification(String alert, String value) {
        NotificationManagerCompat notificationManagerCompat
                = NotificationManagerCompat.from(context);
        Bitmap temp_bitmap = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.icons8_temperature_high_64);
        Bitmap humi_bitmap = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.icons8_humidity_96);
        Bitmap dust_bitmap = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.icons8_dust_40);
        switch (alert) {
            case PM1_HIGH:
                NotificationCompat.Builder builder4
                        = new NotificationCompat.Builder(context, TEMP_HIGH)
                        .setSmallIcon(R.drawable.logo_iot_home)
                        .setContentTitle("Dust pm1 Alert")
                        .setContentText("Dust pm1 too high (" + value + " ºug/m3)")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText("Temperature too high (" + value + " ºug/m3)"))
                        .setChannelId(PMS_CHANNEL_ID)
                        .setLargeIcon(dust_bitmap)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                notificationManagerCompat.notify(PM1_NOTIFICATION_ID, builder4.build());
                break;

            case PM25_HIGH:
                NotificationCompat.Builder builder5
                        = new NotificationCompat.Builder(context, TEMP_HIGH)
                        .setSmallIcon(R.drawable.logo_iot_home)
                        .setContentTitle("Dust pm 2.5 Alert")
                        .setContentText("Dust pm 2.5 too high (" + value + " ºug/m3)")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText("Dust pm 2.5 too high (" + value + " ºug/m3)"))
                        .setChannelId(PMS_CHANNEL_ID)
                        .setLargeIcon(dust_bitmap)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                notificationManagerCompat.notify(PM25_NOTIFICATION_ID, builder5.build());
                break;

            case PM10_HIGH:
                NotificationCompat.Builder builder6
                        = new NotificationCompat.Builder(context, TEMP_HIGH)
                        .setSmallIcon(R.drawable.logo_iot_home)
                        .setContentTitle("Dust pm 10 Alert")
                        .setContentText("Dust pm 10 too high (" + value + " ºug/m3)")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText("Dust pm 10 too high (" + value + " ºug/m3)"))
                        .setChannelId(PMS_CHANNEL_ID)
                        .setLargeIcon(dust_bitmap)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                notificationManagerCompat.notify(PM10_NOTIFICATION_ID, builder6.build());
                break;
            case TEMP_HIGH:
                NotificationCompat.Builder builder
                        = new NotificationCompat.Builder(context, TEMP_HIGH)
                        .setSmallIcon(R.drawable.logo_iot_home)
                        .setContentTitle("Temperature Alert")
                        .setContentText("Temperature too high (" + value + " ºC)")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText("Temperature too high (" + value + " ºC)"))
                        .setChannelId(TEMP_CHANNEL_ID)
                        .setLargeIcon(temp_bitmap)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                notificationManagerCompat.notify(TEMP_NOTIFICATION_ID, builder.build());
                break;
            case TEMP_LOW:
                NotificationCompat.Builder builder1
                        = new NotificationCompat.Builder(context, TEMP_LOW)
                        .setSmallIcon(R.drawable.logo_iot_home)
                        .setContentTitle("Temperature Alert")
                        .setContentText("Temperature too low (" + value + " ºC)")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText("Temperature too low (" + value + " ºC)"))
                        .setChannelId(TEMP_CHANNEL_ID)
                        .setLargeIcon(temp_bitmap)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                notificationManagerCompat.notify(TEMP_NOTIFICATION_ID, builder1.build());
                break;
            case HUMI_HIGH:
                NotificationCompat.Builder builder2
                        = new NotificationCompat.Builder(context, HUMI_HIGH)
                        .setSmallIcon(R.drawable.logo_iot_home)
                        .setContentTitle("Humidity Alert")
                        .setContentText("Humidity too high (" + value + " %)")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText("Humidity too high (" + value + " %)"))
                        .setChannelId(HUMI_CHANNEL_ID)
                        .setLargeIcon(humi_bitmap)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                notificationManagerCompat.notify(HUMI_NOTIFICATION_ID, builder2.build());
                break;
            case HUMI_LOW:
                NotificationCompat.Builder builder3
                        = new NotificationCompat.Builder(context, HUMI_LOW)
                        .setSmallIcon(R.drawable.logo_iot_home)
                        .setContentTitle("Humidity Alert")
                        .setContentText("Humidity too low (" + value + " %)")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText("Humidity too low (" + value + " %)"))
                        .setChannelId(HUMI_CHANNEL_ID)
                        .setLargeIcon(humi_bitmap)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                notificationManagerCompat.notify(HUMI_NOTIFICATION_ID, builder3.build());
                break;
            case TEMP_NORM:
                notificationManagerCompat.cancel(TEMP_NOTIFICATION_ID);
                break;
            case HUMI_NORM:
                notificationManagerCompat.cancel(HUMI_NOTIFICATION_ID);
                break;
            case PMS1_NORM:
                notificationManagerCompat.cancel(PM1_NOTIFICATION_ID);
                break;
            case PMS25_NORM:
                notificationManagerCompat.cancel(PM25_NOTIFICATION_ID);
                break;
            case PMS10_NORM:
                notificationManagerCompat.cancel(PM10_NOTIFICATION_ID);
                break;
        }


    }

    public void createForegroundService(){
        Intent intentService = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0,
                intentService,
                0);
        Notification notification = new NotificationCompat.Builder(this, FOREGROUND_ID)
                .setContentTitle("Foreground service")
                .setContentText("Hello world")
                .setContentIntent(pendingIntent)
                .build();
        startForeground(FOREGROUND_NOTIFICATION_ID, notification);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
