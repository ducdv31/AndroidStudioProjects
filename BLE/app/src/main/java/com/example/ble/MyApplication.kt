package com.example.ble

import android.app.Application
import android.graphics.Region
import com.estimote.coresdk.observation.region.mirror.MirrorRegion
import com.estimote.coresdk.service.BeaconManager

class MyApplication : Application() {

//    private lateinit var beaconManager:BeaconManager

    override fun onCreate() {
        super.onCreate()
//        beaconManager = BeaconManager(applicationContext)

    }
}