package com.example.entertainment.data.repository

import com.example.entertainment.data.bitcoin.model.ResponseBitcoinPrice
import com.example.entertainment.data.movie.model.ResponseMovie
import com.example.entertainment.data.news.model.ResponseNews
import com.example.entertainment.data.weather.model.ResponseWeather

interface Repository {
    suspend fun getDataMovie(): ResponseMovie?

    suspend fun getBitcoinData(): ResponseBitcoinPrice?

    suspend fun getWeatherResponse(
        host: String,
        key: String,
        q: String,
        lat: Float? = null,
        lon: Float? = null,
        lang: String? = null,
        units: String? = null
    ): ResponseWeather?

    suspend fun getFullDataNewsSearch(
        inputSearch: String,
        page: Int = 1,
        from: String? = null,
        sortBy: String? = null
    ): ResponseNews?

    suspend fun getNewsHeadlines(
        page: Int = 1,
        country: String,
        category: String
    ): ResponseNews?
}