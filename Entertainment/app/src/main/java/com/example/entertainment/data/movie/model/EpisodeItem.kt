package com.example.entertainment.data.movie.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class EpisodeItem(
    @SerializedName("episode")
    @Expose
    var episode: String? = null,

    @SerializedName("url")
    @Expose
    var url: String? = null,

    @SerializedName("type")
    @Expose
    var type: String? = null
) : Parcelable