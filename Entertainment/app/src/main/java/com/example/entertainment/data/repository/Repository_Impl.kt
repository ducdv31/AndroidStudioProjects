package com.example.entertainment.data.repository

import com.example.entertainment.data.bitcoin.ApiServiceBitcoin
import com.example.entertainment.data.bitcoin.model.ResponseBitcoinPrice
import com.example.entertainment.data.movie.ApiServiceMovie
import com.example.entertainment.data.movie.model.ResponseMovie
import com.example.entertainment.data.weather.ApiServiceWeather
import com.example.entertainment.data.weather.model.ResponseWeather
import javax.inject.Inject

class Repository_Impl
@Inject constructor(
    private val apiServiceMovie: ApiServiceMovie,
    private val apiServiceBitcoin: ApiServiceBitcoin,
    private val apiServiceWeather: ApiServiceWeather
) : Repository {
    override suspend fun getWeatherResponse(
        host: String,
        key: String,
        q: String,
        lat: Float?,
        lon: Float?,
        lang: String?,
        units: String?
    ): ResponseWeather? = apiServiceWeather.getWeatherResponse(
        host = host,
        key = key,
        q = q,
        lat = lat,
        lon = lon,
        lang = lang,
        units = units
    )

    override suspend fun getBitcoinData(): ResponseBitcoinPrice? =
        apiServiceBitcoin.getBitcoinData()

    override suspend fun getDataMovie(): ResponseMovie? = apiServiceMovie.getDataMovie()
}