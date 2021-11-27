package com.example.entertainment.data.repository

import com.example.entertainment.data.bitcoin.ApiServiceBitcoin
import com.example.entertainment.data.bitcoin.model.ResponseBitcoinPrice
import com.example.entertainment.data.movie.ApiServiceMovie
import com.example.entertainment.data.movie.model.ResponseMovie
import javax.inject.Inject

class Repository_Impl
@Inject constructor(
    private val apiServiceMovie: ApiServiceMovie,
    private val apiServiceBitcoin: ApiServiceBitcoin
) : Repository {
    override suspend fun getBitcoinData(): ResponseBitcoinPrice?  = apiServiceBitcoin.getBitcoinData()

    override suspend fun getDataMovie(): ResponseMovie? = apiServiceMovie.getDataMovie()
}