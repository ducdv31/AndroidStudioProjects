package com.example.entertainment.data.weather

import com.example.entertainment.data.weather.model.ResponseWeather
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiServiceWeather {

    @GET("weather")
    suspend fun getWeatherResponse(
        @Header("x-rapidapi-host") host: String,
        @Header("x-rapidapi-key") key: String,
        @Query("q") q: String,
        @Query("lat") lat: Float? = null,
        @Query("lon") lon: Float? = null,
        @Query("lang") lang: String? = null,
        @Query("units") units: String? = null
    ): ResponseWeather?
}