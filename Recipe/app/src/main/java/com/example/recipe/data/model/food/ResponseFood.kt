package com.example.recipe.data.model.food

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResponseFood(
    @SerializedName("count")
    @Expose
    var count: Int? = null,

    @SerializedName("next")
    @Expose
    var next: String? = null,

    @SerializedName("previous")
    @Expose
    var previous: String? = null,

    @SerializedName("results")
    @Expose
    var results: List<ResultsFood>? = null,
)