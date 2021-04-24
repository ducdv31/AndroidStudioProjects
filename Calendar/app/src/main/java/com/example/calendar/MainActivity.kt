package com.example.calendar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.CalendarView
import java.sql.Time
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val calendar:CalendarView = findViewById(R.id.calendar)
        calendar.firstDayOfWeek = Calendar.MONDAY
        calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            Log.e("day", dayOfMonth.toString())
            Log.e("month", month.toString())
            Log.e("year", year.toString())
        }

    }
}