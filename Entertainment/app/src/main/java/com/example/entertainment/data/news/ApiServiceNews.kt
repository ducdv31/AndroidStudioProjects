package com.example.entertainment.data.news

import com.example.entertainment.data.API_KEY_NEWS
import com.example.entertainment.data.news.model.ResponseNews
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceNews {

    @GET("everything")
    suspend fun getFullDataNewsSearch(
        @Query("apiKey") apiKey: String = API_KEY_NEWS,
        @Query("page") page: Int = 1,
        @Query("q") inputSearch: String,
        @Query("from") from: String? = null,
        @Query("sortBy") sortBy: String? = null
    ): ResponseNews?

    @GET("top-headlines")
    suspend fun getNewsHeadlines(
        @Query("apiKey") apiKey: String = API_KEY_NEWS,
        @Query("page") page: Int = 1,
        @Query("country") country: String,
        @Query("category") category: String
    ): ResponseNews?
}