package com.example.recipe.data.model.food

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResultsFood(
    @SerializedName("pk")
    @Expose
    var pk: Int? = null,

    @SerializedName("title")
    @Expose
    var title: String? = null,

    @SerializedName("publisher")
    @Expose
    var publisher: String? = null,

    @SerializedName("featured_image")
    @Expose
    var featuredImage: String? = null,

    @SerializedName("rating")
    @Expose
    var rating: Int? = null,

    @SerializedName("source_url")
    @Expose
    var sourceUrl: String? = null,

    @SerializedName("description")
    @Expose
    var description: String? = null,

    @SerializedName("cooking_instructions")
    @Expose
    var cookingInstructions: String? = null,

    @SerializedName("ingredients")
    @Expose
    var ingredients: List<String>? = null,

    @SerializedName("date_added")
    @Expose
    var dateAdded: String? = null,

    @SerializedName("date_updated")
    @Expose
    var dateUpdated: String? = null,

    @SerializedName("long_date_added")
    @Expose
    var longDateAdded: Long? = null,

    @SerializedName("long_date_updated")
    @Expose
    var longDateUpdated: Long? = null
) : Parcelable