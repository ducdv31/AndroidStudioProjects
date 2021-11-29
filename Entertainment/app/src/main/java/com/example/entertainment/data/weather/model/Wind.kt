package com.example.entertainment.data.weather.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Wind(
    @SerializedName("speed")
    @Expose
    var speed: Float? = null,

    @SerializedName("deg")
    @Expose
    var deg: Float? = null,

    @SerializedName("gust")
    @Expose
    var gust: Float? = null
)