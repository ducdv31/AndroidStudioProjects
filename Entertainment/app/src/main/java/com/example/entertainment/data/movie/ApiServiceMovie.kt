package com.example.entertainment.data.movie

import com.example.entertainment.data.movie.model.ResponseMovie
import retrofit2.http.GET

interface ApiServiceMovie {

    @GET("QubTry45OOCkTyohU/records/LATEST")
    suspend fun getDataMovie() : ResponseMovie?
}