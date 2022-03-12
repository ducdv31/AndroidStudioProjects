package vn.dv.myvideo.utils

import android.content.res.Resources
import android.util.TypedValue
import kotlin.math.roundToInt

object Utils {
    fun convertDpToPixel(dpValue: Int, resource: Resources): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dpValue.toFloat(),
            resource.displayMetrics
        ).roundToInt()
    }
}