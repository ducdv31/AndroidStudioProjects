package com.example.collapsetoolbar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.graphics.RectF




class MyView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.CYAN
        paint.strokeWidth = 30f
        paint.style = Paint.Style.STROKE
        canvas?.drawLine(300f, 150f, 800f, 150f, paint)
//        canvas?.drawCircle(500f, 1000f, 200f, paint)
        val width = 400f
        val height = 400f

        val left = (getWidth() - width) / 2.0f
        val top = (getHeight() - height) / 2.0f
        canvas?.drawArc(RectF(left, top, left + width, top + height), 45f, 270f, false, paint)
    }
}