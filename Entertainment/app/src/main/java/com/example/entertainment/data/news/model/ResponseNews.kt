package com.example.entertainment.data.news.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResponseNews(
    @SerializedName("status")
    @Expose
    var status: String?,

    @SerializedName("totalResults")
    @Expose
    var totalResults: Int?,

    @SerializedName("articles")
    @Expose
    var articles: List<Article>? = listOf()
)