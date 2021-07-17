package com.example.jobscheduker

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log

class MyJobServices : JobService() {

    companion object {
        val TAG = MyJobServices::class.java.simpleName
    }

    private var cancel: Boolean = false

    override fun onStartJob(params: JobParameters?): Boolean {
        Log.e(TAG, "onStartJob: ")
        onBackgroundWork(params)
        return true
    }

    private fun onBackgroundWork(params: JobParameters?) {
        Thread(Runnable {
            for (i in 1..10) {
                if (cancel) {
                    return@Runnable
                }
                Log.e(TAG, "onBackgroundWork: $i")
                Thread.sleep(1000)
            }
            Log.e(TAG, "job finish")
            jobFinished(params, true)
        }).start()
    }

    override fun onStopJob(params: JobParameters?): Boolean {

        Log.e(TAG, "onStopJob: ")
        cancel = true
        return true
    }
}