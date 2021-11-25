package com.example.recipe.activity.main

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipe.activity.main.convention.FoodEvent
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
    var textSearch: String = EMPTY

    init {
        onTriggerEvent(FoodEvent.EventSearch())
    }

    fun onTriggerEvent(foodEvent: FoodEvent) {
        viewModelScope.launch {
            try {
                when (foodEvent) {
                    is FoodEvent.EventSearch -> {
                        searchFood(foodEvent.input)
                    }
                    is FoodEvent.EventLoadMore -> {
                        loadMoreFood()
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "onTriggerEvent: ${e.cause}")
            }
        }
    }

    private suspend fun searchFood(search: String = EMPTY) {
        textSearch = search
        page.value = 1
        isLoading.value = true
        val listRecipe = repositoryFoodImpl.getListRecipe(
            Authorization = AUTH_FOOD,
            page = page.value,
            query = search
        )
        listRecipe?.let {
            foods.clear()
            foods.addAll(it.results as MutableList<ResultsFood>)
            isLoading.value = false
        }
    }

    private suspend fun loadMoreFood() {
        page.value++
        isLoadMore.value = true
        val listLoadMore = repositoryFoodImpl.getListRecipe(
            Authorization = AUTH_FOOD,
            page = page.value,
            query = textSearch
        )

        listLoadMore?.let {
            isLoadMore.value = false
            it.results?.let { listFood ->
                foods.addAll(listFood)
            }
        }
    }
}