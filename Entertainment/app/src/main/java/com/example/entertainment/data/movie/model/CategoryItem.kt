package com.example.entertainment.data.movie.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryItem(
    @SerializedName("category")
    @Expose
    var category: String? = null,

    @SerializedName("episode")
    @Expose
    var episode: List<EpisodeItem>? = listOf(),

    @SerializedName("imageUrl")
    @Expose
    var imageUrl: String? = null,

    @SerializedName("title")
    @Expose
    var title: String? = null,

    @SerializedName("url")
    @Expose
    var url: String? = null
) : Parcelable