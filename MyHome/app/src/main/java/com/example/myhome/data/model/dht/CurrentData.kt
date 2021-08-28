package com.example.myhome.data.model.dht

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CurrentData(
    @SerializedName("t")
    @Expose
    val t: Int? = 0,
    @SerializedName("h")
    @Expose
    val h: Int? = 0
) {
}