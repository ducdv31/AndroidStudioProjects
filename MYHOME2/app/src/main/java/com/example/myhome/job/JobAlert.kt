package com.example.myhome.job

import android.app.job.JobParameters
import android.app.job.JobService
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.myhome.R
import com.example.myhome.activitymain.model.Dht11Value
import com.example.myhome.tool.Constant
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class JobAlert : JobService() {
    private val TAG = JobAlert::class.java.simpleName
    private var cancel: Boolean = false

    override fun onStartJob(params: JobParameters?): Boolean {
        onBackGroundWork(params)
        return true
    }

    private fun onBackGroundWork(params: JobParameters?) {
        Thread {
            getDataCurrent()
            jobFinished(params, true)
        }.start()
    }

    private fun getDataCurrent() {
        FirebaseDatabase.getInstance().reference.child(Constant.DHT11_CHILD)
            .child(Constant.CURRENT_CHILD)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val dht11Value: Dht11Value? = snapshot.getValue(Dht11Value::class.java)
                    /* check and notify */
                    notifyWithValue(dht11Value)
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
    }

    private fun notifyWithValue(dht11Value: Dht11Value?) {
        val notificationManagerCompat = NotificationManagerCompat.from(this)
        /* temperature */
        val builder1: NotificationCompat.Builder = NotificationCompat.Builder(
            this,
            "For what"
        )
            .setSmallIcon(R.drawable.outline_home_black_36dp)
            .setContentTitle("Temperature Alert")
            .setContentText("Temperature now is ${dht11Value!!.t} ºC")
            .setChannelId(Constant.T_CHANNEL_ID)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        /* humidity */
        val builder2: NotificationCompat.Builder = NotificationCompat.Builder(
            this,
            "For what"
        )
            .setSmallIcon(R.drawable.outline_home_black_36dp)
            .setContentTitle("Humidity Alert")
            .setContentText("Humidity now is ${dht11Value.h} %")
            .setChannelId(Constant.H_CHANNEL_ID)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        /* check and noti */
        if (dht11Value.t !in 20..37) {
            notificationManagerCompat.notify(
                Constant.T_NOTI_ID, builder1.build()
            )
        } else {
            notificationManagerCompat.cancel(Constant.T_NOTI_ID)
        }

        if (dht11Value.h !in 40..70) {
            notificationManagerCompat.notify(
                Constant.H_NOTI_ID, builder2.build()
            )
        } else {
            notificationManagerCompat.cancel(Constant.H_NOTI_ID)
        }

    }

    override fun onStopJob(params: JobParameters?): Boolean {
        cancel = true
        return true
    }
}