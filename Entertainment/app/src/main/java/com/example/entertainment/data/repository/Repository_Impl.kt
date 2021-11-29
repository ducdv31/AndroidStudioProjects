package com.example.entertainment.data.repository

import com.example.entertainment.data.bitcoin.ApiServiceBitcoin
import com.example.entertainment.data.bitcoin.model.ResponseBitcoinPrice
import com.example.entertainment.data.movie.ApiServiceMovie
import com.example.entertainment.data.movie.model.ResponseMovie
import com.example.entertainment.data.news.ApiServiceNews
import com.example.entertainment.data.news.model.ResponseNews
import com.example.entertainment.data.weather.ApiServiceWeather
import com.example.entertainment.data.weather.model.ResponseWeather
import javax.inject.Inject

class Repository_Impl
@Inject constructor(
    private val apiServiceMovie: ApiServiceMovie,
    private val apiServiceBitcoin: ApiServiceBitcoin,
    private val apiServiceWeather: ApiServiceWeather,
    private val apiServiceNews: ApiServiceNews
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

    override suspend fun getFullDataNewsSearch(
        inputSearch: String,
        page: Int,
        from: String?,
        sortBy: String?
    ): ResponseNews? = apiServiceNews.getFullDataNewsSearch(
        inputSearch = inputSearch,
        page = page,
        from = from,
        sortBy = sortBy
    )

    override suspend fun getNewsHeadlines(
        page: Int,
        country: String,
        category: String
    ): ResponseNews? = apiServiceNews.getNewsHeadlines(
        page = page,
        country = country,
        category = category
    )
}