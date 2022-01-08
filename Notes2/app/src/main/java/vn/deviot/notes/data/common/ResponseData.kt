package vn.deviot.notes.data.common

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResponseData<T>(
    @SerializedName("code")
    @Expose
    var code: Int = 0,

    @SerializedName("data")
    @Expose
    var data: T? = null
)