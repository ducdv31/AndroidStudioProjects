package vn.dv.myvideo.common.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.RawValue

data class ResponseVideoPexels<T>(
    @SerializedName("page")
    @Expose
    var page: Int? = null,

    @SerializedName("per_page")
    @Expose
    var perPage: Int? = null,

    @SerializedName("videos")
    @Expose
    var videos: T? = null,

    @SerializedName("total_results")
    @Expose
    var totalResults: Double? = null
)
