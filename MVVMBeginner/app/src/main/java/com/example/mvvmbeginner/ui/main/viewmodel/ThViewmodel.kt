package com.example.mvvmbeginner.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmbeginner.data.api.ApiClient
import com.example.mvvmbeginner.data.api.ApiHelper
import com.example.mvvmbeginner.data.model.DhtTHValue
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ThViewmodel : ViewModel() {

    private val apiHelper = ApiHelper()
    val tValue: MutableLiveData<Int> = MutableLiveData()
    val hValue: MutableLiveData<Int> = MutableLiveData()

    init {
        /*apiHelper.getThData()?.let {
            tValue.value = it.t
            hValue.value = it.h
        }*/ /* it cause block main thread */
        fetchApi()
    }


    fun onUp() {
        tValue.value = tValue.value?.plus(1) ?: 0
        hValue.value = hValue.value?.plus(1) ?: 0
    }

    private fun fetchApi() {
        ApiClient.apiService.getCurrentData("DHT11/Current").enqueue(object : Callback<DhtTHValue> {
            override fun onResponse(call: Call<DhtTHValue>, response: Response<DhtTHValue>) {
                val dhtTHValue: DhtTHValue? = response.body()
                dhtTHValue?.let {
                    tValue.value = it.t
                    hValue.value = it.h
                }
            }

            override fun onFailure(call: Call<DhtTHValue>, t: Throwable) {
            }

        })
    }
}