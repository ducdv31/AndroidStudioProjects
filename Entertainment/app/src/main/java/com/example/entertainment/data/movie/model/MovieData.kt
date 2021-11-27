package com.example.entertainment.data.movie.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MovieData(
    @SerializedName("phimbo")
    @Expose
    var phimBo: List<CategoryItem>? = listOf(),

    @SerializedName("phimle")
    @Expose
    var phimLe: List<CategoryItem>? = listOf(),

    @SerializedName("phimchieurap")
    @Expose
    var phimChieuRap: List<CategoryItem>? = listOf(),

    @SerializedName("phimhoathinh")
    @Expose
    var phimHoatHinh: List<CategoryItem>? = listOf()
)