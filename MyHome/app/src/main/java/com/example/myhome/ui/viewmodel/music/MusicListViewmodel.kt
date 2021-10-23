package com.example.myhome.ui.viewmodel.music

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myhome.data.model.music.MusicData
import com.example.myhome.data.repository.RelaxRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MusicListViewmodel @Inject constructor() : ViewModel() {
    private val TAG = MusicListViewmodel::class.java.simpleName
    val musicData: MutableLiveData<MusicData> = MutableLiveData()

    @Inject
    lateinit var repository: RelaxRepository

    fun getListSong(
        onSuccess: ((musicData: MusicData) -> Unit) = {},
        onFailure: ((t: Throwable) -> Unit) = {}
    ) {
        repository.getListTopMusic().enqueue(object : Callback<MusicData?> {
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