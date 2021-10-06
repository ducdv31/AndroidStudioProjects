package com.example.myhome.data.model.weatherapi

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("name")
    @Expose
    @JsonProperty("name")
    val name: String? = null,

    @SerializedName("region")
    @Expose
    @JsonProperty("region")
    val region: String? = null,

    @SerializedName("country")
    @Expose
    @JsonProperty("country")
    val country: String? = null,

    @SerializedName("lat")
    @Expose
    @JsonProperty("lat")
    val lat: Double? = null,

    @SerializedName("lon")
    @Expose
    @JsonProperty("lon")
    val lon: Double? = null,

    @SerializedName("tz_id")
    @Expose
    @JsonProperty("tz_id")
    val tz_id: String? = null,

    @SerializedName("localtime_epoch")
    @Expose
    @JsonProperty("localtime_epoch")
    val localtime_epoch: Double? = null,

    @SerializedName("localtime")
    @Expose
    @JsonProperty("localtime")
    val localtime: Double? = null
) {
}