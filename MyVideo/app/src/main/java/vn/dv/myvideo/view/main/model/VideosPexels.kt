package vn.dv.myvideo.view.main.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class VideosPexels(
    @SerializedName("id")
    @Expose
    val id: Int? = null,

    @SerializedName("width")
    @Expose
    val width: Int? = null,

    @SerializedName("height")
    @Expose
    val height: Int? = null,

    @SerializedName("duration")
    @Expose
    val duration: Int? = null,

    @SerializedName("full_res")
    @Expose
    val fullRes: @RawValue Any? = null,

    @SerializedName("tags")
    @Expose
    val tags: List<@RawValue Any>? = null,

    @SerializedName("url")
    @Expose
    val url: String? = null,

    @SerializedName("image")
    @Expose
    val image: String? = null,

    @SerializedName("avg_color")
    @Expose
    val avgColor: @RawValue Any? = null,

    @SerializedName("user")
    @Expose
    val user: User? = null,

    @SerializedName("video_files")
    @Expose
    var videoFiles: List<VideoFiles>? = null,

    @SerializedName("video_pictures")
    @Expose
    var videoPictures: List<VideoPictures>? = null

) : Parcelable
