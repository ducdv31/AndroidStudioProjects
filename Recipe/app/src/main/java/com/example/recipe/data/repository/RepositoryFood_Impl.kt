package com.example.recipe.data.repository

import com.example.recipe.data.api.food.ApiServiceFood
import com.example.recipe.data.model.food.ResponseFood
import com.example.recipe.data.model.food.ResultsFood
import javax.inject.Inject

class RepositoryFood_Impl
@Inject constructor(
    private val apiServiceFood: ApiServiceFood
) : RepositoryFood {
    override suspend fun getListRecipe(
        Authorization: String,
        page: Int,
        query: String
    ): ResponseFood? = apiServiceFood.getListRecipe(
        Authorization = Authorization,
        page = page,
        query = query
    )

    override suspend fun getFoodDetailById(Authorization: String, id: Int): ResultsFood? =
        apiServiceFood.getFoodDetailById(
            Authorization = Authorization,
            id = id
        )

}