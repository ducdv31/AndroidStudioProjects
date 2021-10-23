package com.example.myhome.data.repository

import com.example.myhome.data.api.music.ApiMusicHelperImpl
import javax.inject.Inject

class RelaxRepository @Inject constructor(private val apiMusicHelperImpl: ApiMusicHelperImpl) {

    fun getListTopMusic() = apiMusicHelperImpl.getListTopMusic()
}