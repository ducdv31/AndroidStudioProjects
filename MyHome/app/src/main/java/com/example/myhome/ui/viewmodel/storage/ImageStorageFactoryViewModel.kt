package com.example.myhome.ui.viewmodel.storage

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ImageStorageFactoryViewModel(private val lifecycleOwner: LifecycleOwner) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ImageStorageViewModel(lifecycleOwner) as T
    }
}