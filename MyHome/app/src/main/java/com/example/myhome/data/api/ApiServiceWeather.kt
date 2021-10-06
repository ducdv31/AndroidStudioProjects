package com.example.myhome.data.api

import com.example.myhome.data.model.weatherapi.ForecastResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceWeather {

//    @Headers(
//        "x-rapidapi-host: weatherapi-com.p.rapidapi.com",
//        "x-rapidapi-key: 347343136bmsh7a512efd2cc65f4p127eebjsna0e4064b4a5e"
//    )
    @GET("forecast.json")
    fun getForecastNow(
        @Query("q") q: String,
        @Query("days") days: Int
    ) : Call<ForecastResponse>?
}