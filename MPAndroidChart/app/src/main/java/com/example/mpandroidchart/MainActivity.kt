package com.example.mpandroidchart


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    lateinit var btnBarChart: Button
    lateinit var btnPieChart: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnBarChart = findViewById(R.id.btnBarChart)
        btnPieChart = findViewById(R.id.btnPieChart)
        btnBarChart.setOnClickListener {
            val I = Intent(this@MainActivity, BarChartActivity::class.java)
            startActivity(I)
        }
        btnPieChart.setOnClickListener {
            val I = Intent(this@MainActivity, PieChartActivity::class.java)
            startActivity(I)
        }
    }
}