package com.example.entertainment.data.bitcoin.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Time(
    @SerializedName("updated")
    @Expose
    var updated: String? = null,

    @SerializedName("updatedISO")
    @Expose
    var updatedISO: String? = null,

    @SerializedName("updateduk")
    @Expose
    var updatedUk: String? = null
) : Parcelable