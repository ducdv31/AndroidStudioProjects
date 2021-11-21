package com.example.recipe.data.repository

import com.example.recipe.data.model.food.ResponseFood
import com.example.recipe.data.model.food.ResultsFood

interface RepositoryFood {

    suspend fun getListRecipe(
        Authorization: String,
        page: Int,
        query: String
    ): ResponseFood?

    suspend fun getFoodDetailById(
        Authorization: String,
        id: Int
    ): ResultsFood?
}