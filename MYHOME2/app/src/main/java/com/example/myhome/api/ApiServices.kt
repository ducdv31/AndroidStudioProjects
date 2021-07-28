package com.example.myhome.api

import com.example.myhome.activitymain.model.Dht11Value
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServices {
    companion object {
        val gson: Gson
            get() = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create()

        val apiServices: ApiServices
            get() = Retrofit.Builder()
                .baseUrl("https://duc-bkhn-k62.firebaseio.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ApiServices::class.java)
    }

    @GET("{child}.json")
    fun getCurrentData(@Path("child") child: String): Call<Dht11Value>

}