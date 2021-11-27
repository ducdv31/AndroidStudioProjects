package com.example.entertainment.data.bitcoin.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Bpi(
    @SerializedName("USD")
    @Expose
    var USD: UnitCurrency? = null,

    @SerializedName("GBP")
    @Expose
    var GBP: UnitCurrency? = null,

    @SerializedName("EUR")
    @Expose
    var EUR: UnitCurrency? = null
) : Parcelable