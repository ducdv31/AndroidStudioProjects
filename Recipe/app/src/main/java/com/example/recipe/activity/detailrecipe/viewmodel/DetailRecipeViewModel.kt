package com.example.recipe.activity.detailrecipe.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipe.data.constant.AUTH_FOOD
import com.example.recipe.data.model.food.ResultsFood
import com.example.recipe.data.repository.RepositoryFood_Impl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailRecipeViewModel @Inject constructor(
    private val repositoryFoodImpl: RepositoryFood_Impl
) : ViewModel() {

    val detailRecipe: MutableState<ResultsFood> = mutableStateOf(ResultsFood())

    fun getDetailRecipe(
        id: Int,
        isLoading: MutableState<Boolean> = mutableStateOf(false)
    ): ResultsFood {
        viewModelScope.launch {
            isLoading.value = true
            val data = repositoryFoodImpl.getFoodDetailById(
                Authorization = AUTH_FOOD,
                id = id
            )

            detailRecipe.value = data ?: ResultsFood()
            isLoading.value = false
        }
        return detailRecipe.value
    }
}