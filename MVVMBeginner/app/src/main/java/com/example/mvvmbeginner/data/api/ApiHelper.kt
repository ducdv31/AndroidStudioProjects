package com.example.mvvmbeginner.data.api

import com.example.mvvmbeginner.data.model.DhtTHValue
import retrofit2.Call

class ApiHelper {

    fun getThData(): DhtTHValue? =
        ApiClient.apiService
            .getCurrentData("DHT11/Current")
            .execute().body()

}