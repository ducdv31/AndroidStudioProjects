package vn.dv.myvideo.view.main.model

import android.os.Parcelable
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class VideoPictures(
    @SerializedName("id")
    @Expose
    private var id: Int? = null,

    @SerializedName("nr")
    @Expose
    private var nr: Int? = null,

    @SerializedName("picture")
    @Expose
    private var picture: String? = null
) : Parcelable
