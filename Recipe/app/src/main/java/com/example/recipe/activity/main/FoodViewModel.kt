package com.example.recipe.activity.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipe.data.constant.AUTH_FOOD
import com.example.recipe.data.constant.EMPTY
import com.example.recipe.data.model.food.ResultsFood
import com.example.recipe.data.repository.RepositoryFood_Impl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodViewModel @Inject constructor(
    private val repositoryfoodImpl: RepositoryFood_Impl
) : ViewModel() {

    var foods: MutableState<List<ResultsFood>?> = mutableStateOf(listOf())

    init {
        searchFood()
    }

    fun searchFood() {
        viewModelScope.launch {
            val listRecipe = repositoryfoodImpl.getListRecipe(
                AUTH_FOOD,
                1,
                EMPTY
            )
            listRecipe?.let {
                foods.value = it.results
            }
        }
    }
}