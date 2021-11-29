package com.example.entertainment.data.weather.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Main(
    @SerializedName("temp")
    @Expose
    var temp: Float? = null,

    @SerializedName("feels_like")
    @Expose
    var feelsLike: Float? = null,

    @SerializedName("temp_min")
    @Expose
    var tempMin: Float? = null,

    @SerializedName("temp_max")
    @Expose
    var tempMax: Float? = null,

    @SerializedName("pressure")
    @Expose
    var pressure: Float? = null,

    @SerializedName("humidity")
    @Expose
    var humidity: Float? = null,

    @SerializedName("sea_level")
    @Expose
    var seaLevel: Float? = null,

    @SerializedName("grnd_level")
    @Expose
    var grnd_level: Float? = null
)