package com.example.constrainslayout

import android.app.ActionBar
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Button
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var showPopUp: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showPopUp = findViewById(R.id.show_pop_up)
        val view = LayoutInflater.from(this)
            .inflate(R.layout.pop_up, null, false)

        showPopUp.setOnClickListener {
            val pop = PopupWindow(
                view,
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.WRAP_CONTENT,
                true
            )
            pop.showAtLocation(view, Gravity.CENTER, 0, 0)
        }
    }
}