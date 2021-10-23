package com.example.myhome.data.api

import com.example.myhome.data.model.dht.CurrentData
import com.example.myhome.utils.Constants
import retrofit2.Call
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiServices: ApiServices) : ApiHelper {
    override fun getCurrentData(): Call<CurrentData>? =
        apiServices.getCurrentData(Constants.DHT11_CHILD)
}