package com.example.myhome.data.api

import com.example.myhome.data.model.dht.CurrentData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServices {

    @GET("{sensorName}/Current/.json")
    fun getCurrentData(
        @Path("sensorName") sensorName: String
    ): Call<CurrentData>?
}