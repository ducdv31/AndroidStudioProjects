package com.example.entertainment.data.bitcoin

import com.example.entertainment.data.bitcoin.model.ResponseBitcoinPrice
import retrofit2.http.GET

interface ApiServiceBitcoin {

    @GET("currentprice.json")
    suspend fun getBitcoinData(): ResponseBitcoinPrice?
}