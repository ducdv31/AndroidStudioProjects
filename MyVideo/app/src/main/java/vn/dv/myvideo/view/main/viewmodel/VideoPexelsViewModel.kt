package vn.dv.myvideo.view.main.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import vn.dv.myvideo.repo.Repository_Impl
import vn.dv.myvideo.utils.Const
import vn.dv.myvideo.view.main.model.VideosPexels
import javax.inject.Inject

@HiltViewModel
class VideoPexelsViewModel @Inject constructor(
    private val repositoryImpl: Repository_Impl
) : ViewModel() {
    private val TAG = VideoPexelsViewModel::class.java.simpleName

    private val coroutineScope = CoroutineScope(IO)
    private val _videos: MutableLiveData<List<VideosPexels>?> = MutableLiveData()
    val videos: LiveData<List<VideosPexels>?> = _videos

    fun getPopularVideo() {
        viewModelScope.launch {
            val job = coroutineScope.async {
                repositoryImpl.getVideoPopular(Const.API_KEY)
            }
            try {
                val response = job.await()
                _videos.value = response?.videos
            } catch (e: Exception) {
                Log.e(TAG, "getPopularVideo: ${e.message}")
            }
        }
    }

}