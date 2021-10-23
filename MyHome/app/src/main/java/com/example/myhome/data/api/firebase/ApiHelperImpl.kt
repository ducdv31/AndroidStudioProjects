package com.example.myhome.data.api.firebase

import com.example.myhome.data.model.dht.CurrentData
import retrofit2.Call
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiServices: ApiServices) : ApiHelper {
    override fun getCurrentData(sensorName: String): Call<CurrentData>? =
        apiServices.getCurrentData(sensorName)
}