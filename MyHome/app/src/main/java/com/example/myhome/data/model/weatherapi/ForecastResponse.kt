package com.example.myhome.data.model.weatherapi

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    @SerializedName("location")
    @Expose
    @JsonProperty("location")
    val location: Location? = null
) {
}