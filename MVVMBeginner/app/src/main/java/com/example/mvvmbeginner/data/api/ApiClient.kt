package com.example.mvvmbeginner.data.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    companion object {
        private val gson: Gson
            get() = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create()

        val apiService: ApiService = Retrofit.Builder()
            .baseUrl("https://duc-bkhn-k62.firebaseio.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService::class.java)
    }
}