package com.example.recipe.data.api.food

import com.example.recipe.data.model.food.ResponseFood
import com.example.recipe.data.model.food.ResultsFood
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiServiceFood {

    @GET("search/")
    suspend fun getListRecipe(
        @Header("Authorization") Authorization: String,
        @Query("page") page: Int,
        @Query("query") query: String
    ): ResponseFood?

    @GET("get/")
    suspend fun getFoodDetailById(
        @Header("Authorization") Authorization: String,
        @Query("id") id: Int
    ): ResultsFood?
}