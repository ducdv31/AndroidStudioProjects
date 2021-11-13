package com.example.collapsetoolbar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.TextView

class MyTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : androidx.appcompat.widget.AppCompatTextView(context, attrs) {

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.strokeWidth = 20f
        paint.color = Color.RED
        val lineTopLength = 500f
        val lineTopStartX = 300f
        val lineTopStartY = 300f

        canvas?.drawLine(
            lineTopStartX,
            lineTopStartY,
            lineTopStartX + lineTopLength,
            lineTopStartY,
            paint
        )
    }

    override fun onDrawForeground(canvas: Canvas?) {
        super.onDrawForeground(canvas)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.strokeWidth = 20f
        paint.color = Color.RED
        val lineTopLength = 500f
        val lineTopStartX = 300f
        val lineTopStartY = 300f

        canvas?.drawLine(
            lineTopStartX,
            lineTopStartY,
            lineTopStartX + lineTopLength,
            lineTopStartY,
            paint
        )
    }
}