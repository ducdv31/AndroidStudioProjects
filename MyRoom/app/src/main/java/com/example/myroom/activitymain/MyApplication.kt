package com.example.myroom.activitymain

import android.app.Application
import com.example.myroom.sharedpreference.DataLocalManager

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DataLocalManager.init(applicationContext)
    }
}