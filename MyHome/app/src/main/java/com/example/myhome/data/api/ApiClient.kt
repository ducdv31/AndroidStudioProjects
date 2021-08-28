package com.example.myhome.data.api

import com.example.myhome.utils.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    companion object {
        private val gson: Gson = GsonBuilder().setDateFormat(Constants.DATE_FORMAT_GSON).create()

        private val apiClient: ApiServices = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiServices::class.java)

        fun getClient(): ApiServices {
            return apiClient
        }
    }
}