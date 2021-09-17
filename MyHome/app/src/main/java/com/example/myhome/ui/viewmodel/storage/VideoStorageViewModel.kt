package com.example.myhome.ui.viewmodel.storage

import androidx.lifecycle.ViewModel
import com.example.myhome.ui.adapter.storage.video.VideoStorageAdapter
import com.example.myhome.utils.Constants
import com.google.firebase.storage.FirebaseStorage

class VideoStorageViewModel : ViewModel() {

    fun getListVideo(
        videoStorageAdapter: VideoStorageAdapter,
        onSuccess: (() -> Unit) = {},
        onFailure: ((e: Exception) -> Unit) = {}
    ) {
        val list: MutableList<String> = mutableListOf()
        FirebaseStorage.getInstance().reference
            .child(Constants.VIDEO_PATH_STORAGE)
            .listAll().addOnSuccessListener {
                it.items.forEach { ref ->
                    list.add(ref.toString())
                    videoStorageAdapter.setData(list)
                }
                onSuccess.invoke()
            }.addOnFailureListener {
                onFailure.invoke(it)
            }
    }
}