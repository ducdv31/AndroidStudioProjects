package com.example.myhome

import android.app.Application
import com.example.myhome.data.repository.MyDataLocal
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        MyDataLocal.init(applicationContext)
    }
}