package com.example.myhome.ui.viewmodel.dht

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DhtFactoryViewModel(private val activity: Activity) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DhtViewmodel(activity) as T
    }
}