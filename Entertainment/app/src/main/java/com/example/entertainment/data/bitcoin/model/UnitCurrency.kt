package com.example.entertainment.data.bitcoin.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UnitCurrency(
    @SerializedName("code")
    @Expose
    var code: String? = null,

    @SerializedName("symbol")
    @Expose
    var symbol: String? = null,

    @SerializedName("rate")
    @Expose
    var rate: String? = null,

    @SerializedName("description")
    @Expose
    var description: String? = null,

    @SerializedName("rate_float")
    @Expose
    var rateFloat: Float? = null
) : Parcelable