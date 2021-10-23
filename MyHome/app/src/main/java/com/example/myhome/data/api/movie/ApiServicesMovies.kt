package com.example.myhome.data.api.movie

import com.example.myhome.data.model.movie.Movies
import retrofit2.Call
import retrofit2.http.GET

interface ApiServicesMovies {

    @GET("LATEST")
    fun getListMovies(): Call<Movies?>
}