package com.example.myhome.data.model.music

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TypeSongs(
    @SerializedName("name")
    @Expose
    val name: String? = null,

    @SerializedName("url")
    @Expose
    val url: String? = null,

    @SerializedName("songs")
    @Expose
    val songs: List<SongItem>? = null
) {
}