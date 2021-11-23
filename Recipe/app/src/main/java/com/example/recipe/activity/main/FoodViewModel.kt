package com.example.recipe.activity.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipe.data.constant.AUTH_FOOD
import com.example.recipe.data.constant.EMPTY
import com.example.recipe.data.model.food.ResultsFood
import com.example.recipe.data.repository.RepositoryFood_Impl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

const val MAX_PAGE_FOOD = 30

@HiltViewModel
class FoodViewModel @Inject constructor(
    private val repositoryFoodImpl: RepositoryFood_Impl
) : ViewModel() {

    var foods: SnapshotStateList<ResultsFood?> = mutableStateListOf()
    val isLoading: MutableState<Boolean> = mutableStateOf(false)
    val isLoadMore: MutableState<Boolean> = mutableStateOf(false)
    var page: MutableState<Int> = mutableStateOf(1)

    init {
        searchFood()
    }

    private fun searchFood() {
        viewModelScope.launch {
            page.value = 1
            isLoading.value = true
            val listRecipe = repositoryFoodImpl.getListRecipe(
                Authorization = AUTH_FOOD,
                page = page.value,
                query = EMPTY
            )
            listRecipe?.let {
                foods.clear()
                foods.addAll(it.results as MutableList<ResultsFood>)
                isLoading.value = false
            }
        }
    }

    fun loadMoreFood() {
        page.value++
        viewModelScope.launch {
            isLoadMore.value = true
            val listLoadMore = repositoryFoodImpl.getListRecipe(
                Authorization = AUTH_FOOD,
                page = page.value,
                query = EMPTY
            )

            listLoadMore?.let {
                isLoadMore.value = false
                it.results?.let { listFood ->
                    foods.addAll(listFood)
                }
            }
        }
    }
}