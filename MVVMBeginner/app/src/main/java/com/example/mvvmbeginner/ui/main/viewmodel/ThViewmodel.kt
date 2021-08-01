package com.example.mvvmbeginner.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ThViewmodel : ViewModel() {
    private val _tValue: MutableLiveData<Int> = MutableLiveData()
    private val _hValue: MutableLiveData<Int> = MutableLiveData()

    val tValue: LiveData<Int> = _tValue
    val hValue: LiveData<Int> = _hValue

    fun onUp() {
        _tValue.value = _tValue.value?.plus(1) ?: 0
        _hValue.value = _hValue.value?.plus(1) ?: 0
    }
}