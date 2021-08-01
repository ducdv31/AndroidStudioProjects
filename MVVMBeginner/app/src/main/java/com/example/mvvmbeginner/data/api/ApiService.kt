package com.example.mvvmbeginner.data.api

import com.example.mvvmbeginner.data.model.DhtTHValue
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("{child}.json")
    fun getCurrentData(@Path("child") child: String): Call<DhtTHValue>
}