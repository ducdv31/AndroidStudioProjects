package com.example.entertainment.data.movie.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResponseMovie(
    @SerializedName("sourceUrl")
    @Expose
    var sourceUrl: String? = null,

    @SerializedName("lastUpdatedAtApify")
    @Expose
    var lastUpdatedAtApify: String? = null,

    @SerializedName("author")
    @Expose
    var author: String? = null,

    @SerializedName("lastUpdatedAtSource")
    @Expose
    var lastUpdatedAtSource: String? = null,

    @SerializedName("phim")
    @Expose
    var phim: MovieData? = null
)