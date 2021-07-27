package com.example.myhome

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.example.myhome.tool.Constant

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val tChannel = NotificationChannel(
                Constant.T_CHANNEL_ID,
                getString(R.string.t_notification_alert_name),
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = getString(R.string.t_description_info_noti_alert)
            }

            val hChannel = NotificationChannel(
                Constant.H_CHANNEL_ID,
                getString(R.string.h_notification_alert_name),
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = getString(R.string.t_description_info_noti_alert)
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(tChannel)
            notificationManager.createNotificationChannel(hChannel)
        }
    }
}