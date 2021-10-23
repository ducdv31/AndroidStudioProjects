package com.example.myhome.data.api

import com.example.myhome.data.model.dht.CurrentData
import retrofit2.Call

interface ApiHelper {
    fun getCurrentData() : Call<CurrentData>?
}