package com.example.myhome.ui.viewmodel.music

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myhome.data.api.ApiClientMusics
import com.example.myhome.data.api.ApiServicesMusics
import com.example.myhome.data.model.music.MusicData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MusicListViewmodel : ViewModel() {
    private val TAG = MusicListViewmodel::class.java.simpleName
    val musicData: MutableLiveData<MusicData> = MutableLiveData()

    private val apiClientMusics: ApiServicesMusics by lazy {
        ApiClientMusics.getClient()
    }

    fun getListSong(
        onSuccess: ((musicData: MusicData) -> Unit) = {},
        onFailure: ((t: Throwable) -> Unit) = {}
    ) {
        apiClientMusics.getListTopMusic().enqueue(object : Callback<MusicData?> {
            override fun onResponse(call: Call<MusicData?>, response: Response<MusicData?>) {
                musicData.value = response.body()
                response.body()?.let(onSuccess)
            }

            override fun onFailure(call: Call<MusicData?>, t: Throwable) {
                onFailure.invoke(t)
            }

        })
    }
}