package com.example.entertainment.data.weather.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Sys(
    @SerializedName("type")
    @Expose
    var type: Int? = null,

    @SerializedName("id")
    @Expose
    var id: Int?,

    @SerializedName("country")
    @Expose
    var country: String?,

    @SerializedName("sunrise")
    @Expose
    var sunrise: Double?,

    @SerializedName("sunset")
    @Expose
    var sunset: Double?
)