package com.example.framelayout

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    lateinit var tvFrameOk: TextView
    lateinit var frameLayout: FrameLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvFrameOk = findViewById(R.id.tv_frame_ok)
        frameLayout = findViewById(R.id.ln_frame_hello)

        val handler = Handler()
        handler.postDelayed({
            frameLayout.visibility = View.GONE
            tvFrameOk.visibility = View.VISIBLE
        }, 2000)
    }
}