package com.example.learninggit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println("Hello 1")

        println("Hello 2")

        println("Hello 3")

        println("Squash 1")
        println("Squash 2")
        println("Squash 3")
    }
}