package com.example.myhome.data.model.music

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MusicData(
    @SerializedName("songs")
    @Expose
    val songs: Songs? = null,

    @SerializedName("lastUpdatedAtSource")
    @Expose
    val lastUpdatedAtSource: String? = null,

    @SerializedName("sourceUrl")
    @Expose
    val sourceUrl: String? = null
)