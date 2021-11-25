package com.example.recipe.activity.main.convention

import com.example.recipe.data.constant.EMPTY

sealed class FoodEvent {
    data class EventSearch(
        val input: String = EMPTY
    ) : FoodEvent()

    object EventLoadMore : FoodEvent()
}
