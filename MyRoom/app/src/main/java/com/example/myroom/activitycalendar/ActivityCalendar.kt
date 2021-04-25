package com.example.myroom.activitycalendar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.FragmentTransaction
import com.example.myroom.R
import com.example.myroom.activitycalendar.fragment.CalendarFragment

class ActivityCalendar : AppCompatActivity() {

    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.title = "Day of work"
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val fragmentTransition:FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.frame_calendar, CalendarFragment())
        fragmentTransition.commit()
        
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> onBackPressed()
        }
        return true
    }
}