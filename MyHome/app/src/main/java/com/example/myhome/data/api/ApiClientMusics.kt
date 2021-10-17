package com.example.myhome.data.api

import com.example.myhome.utils.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClientMusics {
    private val gson: Gson = GsonBuilder().setDateFormat(Constants.DATE_FORMAT_GSON).create()

    fun getClient(): ApiServicesMusics {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_MUSIC)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiServicesMusics::class.java)
    }
}