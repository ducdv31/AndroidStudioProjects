package com.example.myhome.data.api.firebase

import com.example.myhome.data.model.dht.CurrentData
import retrofit2.Call

interface ApiHelper {
    fun getCurrentData(sensorName: String): Call<CurrentData>?
}