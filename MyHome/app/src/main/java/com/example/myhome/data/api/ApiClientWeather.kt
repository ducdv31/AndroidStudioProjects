package com.example.myhome.data.api

import com.example.myhome.utils.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.DateFormat

class ApiClientWeather {
    companion object {
        val request = Request.Builder()
            .url("https://weatherapi-com.p.rapidapi.com/forecast.json?q=Hanoi&days=3")
            .get()
            .addHeader("x-rapidapi-host", "weatherapi-com.p.rapidapi.com")
            .addHeader("x-rapidapi-key", "347343136bmsh7a512efd2cc65f4p127eebjsna0e4064b4a5e")
            .build()

        private val gson: Gson = GsonBuilder().setDateFormat(DateFormat.FULL, DateFormat.FULL).create()

        private val okHttpClient: OkHttpClient.Builder = OkHttpClient().newBuilder()
            .addInterceptor {
                val request = it.request().newBuilder()
                    .addHeader("x-rapidapi-host", "weatherapi-com.p.rapidapi.com")
                    .addHeader(
                        "x-rapidapi-key",
                        "347343136bmsh7a512efd2cc65f4p127eebjsna0e4064b4a5e"
                    )
                    .build()
                it.proceed(request)
            }

        fun getClient(): ApiServiceWeather {
            return Retrofit.Builder()
                .baseUrl(Constants.BASE_URL_WEATHER)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient.build())
                .build()
                .create(ApiServiceWeather::class.java)
        }
    }
}