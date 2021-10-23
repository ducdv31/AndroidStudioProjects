package com.example.myhome.data.api.music

import com.example.myhome.data.model.music.MusicData
import retrofit2.Call
import javax.inject.Inject

class ApiMusicHelperImpl @Inject constructor(private val apiServicesMusics: ApiServicesMusics) :
    ApiMusicHelper {
    override fun getListTopMusic(): Call<MusicData?> {
        return apiServicesMusics.getListTopMusic()
    }
}