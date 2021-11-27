package com.example.entertainment.data.bitcoin.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponseBitcoinPrice(
    @SerializedName("time")
    @Expose
    var time: Time? = null,

    @SerializedName("disclaimer")
    @Expose
    var disclaimer: String? = null,

    @SerializedName("chartName")
    @Expose
    var chartName: String? = null,

    @SerializedName("bpi")
    @Expose
    var bpi: Bpi? = null
) : Parcelable