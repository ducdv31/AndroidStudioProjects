package com.example.myhome.ui.viewmodel.storage

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.example.myhome.ui.adapter.storage.image.ImageStorageAdapter
import com.example.myhome.utils.Constants
import com.google.firebase.storage.FirebaseStorage

class ImageStorageViewModel(lifecycleOwner: LifecycleOwner) : ViewModel() {

    private val TAG = ImageStorageViewModel::class.java.simpleName


    fun getListImage(
        imageStorageAdapter: ImageStorageAdapter,
        onSuccess: (() -> Unit) = {},
        onFailure: ((exception: Exception) -> Unit) = {}
    ) {
        val list: MutableList<String> = mutableListOf()
        FirebaseStorage.getInstance().reference
            .child(Constants.IMAGE_PATH_STORAGE)
            .listAll().addOnSuccessListener {
                if (it.items.isEmpty()) {
                    imageStorageAdapter.setData(list)
                }
                it.items.forEach { ref ->
                    ref.downloadUrl.addOnSuccessListener { uri ->
                        list.add(uri.toString())
                        imageStorageAdapter.setData(list)
                    }
                }
                onSuccess.invoke()
            }.addOnFailureListener {
                onFailure.invoke(it)
            }
    }
}