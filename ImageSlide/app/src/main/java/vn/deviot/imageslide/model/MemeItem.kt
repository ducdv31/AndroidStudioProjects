package vn.deviot.imageslide.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MemeItem(
    val box_count: Int,
    val height: Int,
    val id: String,
    val name: String,
    val url: String,
    val width: Int
) : Parcelable