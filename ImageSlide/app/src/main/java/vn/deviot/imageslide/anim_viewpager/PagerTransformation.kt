package vn.deviot.imageslide.anim_viewpager

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

class PagerTransformation : ViewPager2.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        val heightView = page.height
        page.apply {
            scaleY = (0.75 + (1 - abs(position) * 0.25)).toFloat()
            alpha = (0.5 + 0.5 * (1 - abs(position))).toFloat()
            translationY = heightView * -position
            rotation = position * 360
            when {
                position < 0 -> translationY = heightView * -position
                position > 0 -> translationY = heightView * position
            }
        }
    }
}