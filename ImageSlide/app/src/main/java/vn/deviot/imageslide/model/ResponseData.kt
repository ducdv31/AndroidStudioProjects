package vn.deviot.imageslide.model

import com.google.gson.annotations.SerializedName

data class ResponseData(
    @SerializedName("data")
    var data: Meme? = null
)