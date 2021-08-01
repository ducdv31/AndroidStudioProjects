package com.example.mvvmbeginner.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import retrofit2.Call

data class DhtTHValue(
    @SerializedName("t")
    @Expose
    val t: Int? = 0,
    @SerializedName("h")
    @Expose
    val h: Int? = 0
) {
}