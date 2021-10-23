package com.example.myhome.data.api.music

import com.example.myhome.data.model.music.MusicData
import retrofit2.Call

interface ApiMusicHelper {
    fun getListTopMusic(): Call<MusicData?>
}