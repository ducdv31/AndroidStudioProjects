package com.example.myhome.data.model.music

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SongItem(
    @SerializedName("avatar")
    @Expose
    val avatar: String? = null,

    @SerializedName("bgImage")
    @Expose
    val bgImage: String? = null,

    @SerializedName("coverImage")
    @Expose
    val coverImage: String? = null,

    @SerializedName("creator")
    @Expose
    val creator: String? = null,

    @SerializedName("lyric")
    @Expose
    val lyric: String? = null,

    @SerializedName("music")
    @Expose
    val music: String? = null,

    @SerializedName("title")
    @Expose
    val title: String? = null,

    @SerializedName("url")
    @Expose
    val url: String? = null,
) {
}