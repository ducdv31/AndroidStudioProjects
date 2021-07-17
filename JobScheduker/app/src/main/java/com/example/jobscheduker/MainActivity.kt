package com.example.jobscheduker

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val JOB_ID: Int = 1
    private lateinit var start: Button
    lateinit var stop: Button
    private val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        start = findViewById(R.id.start_job)
        stop = findViewById(R.id.stop_job)

        start.setOnClickListener {
            onStartScheduler()
        }
        stop.setOnClickListener {
            onStopScheduler()
        }
    }

    private fun onStartScheduler() {
        Log.e(TAG, "onStartScheduler: ", )
        val componentName = ComponentName(this, MyJobServices::class.java)
        val jobInfo = JobInfo.Builder(JOB_ID, componentName)
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED) // Dung wifi
            .setPersisted(true) // Khoi dong lai van chay bth
            .setPeriodic(15 * 60 * 1000L)
            .build()

        val jobScheduler: JobScheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
        jobScheduler.schedule(jobInfo)
    }

    private fun onStopScheduler() {
        Log.e(TAG, "onStopScheduler: ", )
        val jobScheduler: JobScheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
        jobScheduler.cancel(JOB_ID)
    }
}