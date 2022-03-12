package vn.dv.myvideo.view.main.model

import android.os.Parcelable
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class VideoFiles(
    @SerializedName("id")
    @Expose
    var id: Int? = null,

    @SerializedName("quality")
    @Expose
    var quality: String? = null,

    @SerializedName("file_type")
    @Expose
    var fileType: String? = null,

    @SerializedName("width")
    @Expose
    var width: Int? = null,

    @SerializedName("height")
    @Expose
    var height: Int? = null,

    @SerializedName("link")
    @Expose
    var link: String? = null
) : Parcelable