package com.example.myhome.data.api

import com.example.myhome.data.model.music.MusicData
import retrofit2.Call
import retrofit2.http.GET

interface ApiServicesMusics {

    @GET("LATEST")
    fun getListTopMusic(): Call<MusicData?>
}