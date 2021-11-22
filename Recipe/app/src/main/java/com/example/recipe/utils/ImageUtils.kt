package com.example.recipe.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.recipe.R
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable

object ImageUtils {
    @Composable
    fun loadImage(
        url: String? = null,
        @DrawableRes defaultImage: Int = R.drawable.ic_launcher_foreground
    ): MutableState<Bitmap?> {
        val bitmap: MutableState<Bitmap?> = remember { mutableStateOf(null) }

        val shimmer =
            Shimmer.AlphaHighlightBuilder()// The attributes for a ShimmerDrawable is set by this builder
                .setDuration(1800) // how long the shimmering animation takes to do one full sweep
                .setBaseAlpha(0.7f) //the alpha of the underlying children
                .setHighlightAlpha(0.6f) // the shimmer alpha amount
                .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                .setAutoStart(true)
                .build()

        // This is the placeholder for the imageView
        val shimmerDrawable = ShimmerDrawable().apply {
            setShimmer(shimmer)
        }

        Glide.with(LocalContext.current)
            .asBitmap()
            .load(url)
            .placeholder(shimmerDrawable)
            .error(defaultImage)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    bitmap.value = resource
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }

            })
        return bitmap
    }

    /**
     * Load with Glide
     */
    /*resultsFood.featuredImage?.let { url ->
        val image = loadImage(
            url = url,
            R.drawable.ic_launcher_foreground
        ).value
        image?.let { img ->
            Image(
                bitmap = img.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .padding(all = 8.dp)
                    .clip(RoundedCornerShape(10)),
                contentScale = ContentScale.Crop
            )
        }
    }*/
}