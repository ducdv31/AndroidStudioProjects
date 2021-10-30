package com.example.collapsetoolbar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        val collapse : CollapsingToolbarLayout = findViewById(R.id.collapse_layout)
    }
}