package vn.deviot.imageslide.anim_viewpager

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

class PagerTransformation : ViewPager2.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        page.apply {
            val r = 1 - abs(position)
            scaleY = (0.75 + r * 0.25).toFloat()
            alpha = (0.5 + 0.5 * r).toFloat()
        }
    }
}