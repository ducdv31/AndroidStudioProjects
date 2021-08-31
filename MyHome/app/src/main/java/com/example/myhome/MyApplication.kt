package com.example.myhome

import android.app.Application
import com.example.myhome.data.repository.MyDataLocal

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        MyDataLocal.init(applicationContext)
    }
}