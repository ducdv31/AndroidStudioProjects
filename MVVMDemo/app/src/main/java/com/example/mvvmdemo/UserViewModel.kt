package com.example.mvvmdemo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {

    private var _number: MutableLiveData<Int> = MutableLiveData(0)
    val number: LiveData<Int> = _number

    fun setNum(value:Int){
        _number.value = value
    }

    fun onClickUp(){
        _number.value = _number.value?.plus(1)
        Log.e("TAG", "onClickUp: ${_number.value}")
    }
}