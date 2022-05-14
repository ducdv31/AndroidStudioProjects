package vn.dv.todolist.infrastructure.core

import android.content.res.Resources
import android.util.TypedValue
import kotlin.math.roundToInt


object Utils {
    fun convertDpToPixel(dpValue: Float, resources: Resources): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dpValue,
            resources.displayMetrics
        ).roundToInt()
    }
}