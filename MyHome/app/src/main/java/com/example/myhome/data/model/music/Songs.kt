package com.example.myhome.data.model.music

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Songs(
    @SerializedName("top100_VN")
    @Expose
    val topVN: List<TypeSongs>?,

    @SerializedName("top100_AM")
    @Expose
    val topAM: List<TypeSongs>?,

    @SerializedName("top100_CA")
    @Expose
    val topCA: List<TypeSongs>?,

    @SerializedName("top100_KL")
    @Expose
    val topKL: List<TypeSongs>?
) {
}