package vn.dv.myvideo.view.main.enum

import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import vn.dv.myvideo.R

enum class EnumHomeViewpager(
    @IdRes val idMenu: Int,
    @DrawableRes val icon: Int,
    @StringRes val title: Int
) {
    PopularImage(
        R.id.popular_image,
        R.drawable.ic_image_black_24dp,
        R.string.popular_image
    ),
    PopularVideo(
        R.id.popular_video,
        R.drawable.ic_videocam_black_24dp,
        R.string.popular_video
    )
}