package com.example.myhome.data.api.movie

import com.example.myhome.utils.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClientMovies {
    private val gson: Gson = GsonBuilder().setDateFormat(Constants.DATE_FORMAT_GSON).create()

    fun getClient(): ApiServicesMovies {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_MOVIE)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiServicesMovies::class.java)
    }
}