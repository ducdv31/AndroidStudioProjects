package com.example.mvvm.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {

    private val num: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>().also {
            loadUsers()
        }
    }

    fun getNum(): LiveData<Int> {
        return num
    }

    private fun loadUsers() {
        // Do an asynchronous operation to fetch users.
    }
}