package com.example.myroom.activitycalendar.fragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myroom.R
import com.example.myroom.activitycalendar.ActivityCalendar
import com.example.myroom.activitycalendar.model.TaskUser
import com.example.myroom.activitycalendar.rcvadapter.UserCalendarAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CalendarFragment : Fragment() {

    private lateinit var userCalendarAdapter: UserCalendarAdapter
    private lateinit var handler: Handler
    private lateinit var activityCalendar: ActivityCalendar
    private lateinit var databaseReference: DatabaseReference
    private lateinit var Date: String
    //    private lateinit var listUser: MutableList<UserCalendar>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val calendarView = inflater.inflate(R.layout.fragment_calendar, container, false)
        val calendar: CalendarView = calendarView.findViewById(R.id.calender_view)
        val recyclerView: RecyclerView = calendarView.findViewById(R.id.rcv_user_calendar)
        activityCalendar = activity as ActivityCalendar
        activityCalendar.actionBar?.title = "Day of work"
        databaseReference = FirebaseDatabase.getInstance().reference
        Date = activityCalendar.getTime()

        userCalendarAdapter =
            UserCalendarAdapter(requireContext(), object : UserCalendarAdapter.IClickUserCalendar {
                override fun onClickUserCalendar(taskUser: TaskUser) {
                    /* go to task user fragment - Input: Task, Date */
                    activityCalendar.gotoTaskUserFragment(taskUser, Date)
                }

            })
//        listUser = mutableListOf()
        val linearLayoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)


        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = userCalendarAdapter

        calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            /* get time and set data to list user */
            getUserFromDay(dayOfMonth, month, year)
        }

        activityCalendar.getUser(userCalendarAdapter, activityCalendar.getTime())

        return calendarView
    }

    private fun getUserFromDay(dayOfMonth: Int, month: Int, year: Int) {
        val Day: String = dayOfMonth.toString()
        var Month = ""
        Month = if (month + 1 < 10) {
            "0" + (month + 1).toString()
        } else {
            (month + 1).toString()
        }
        val Year: String = year.toString()
        Date = "$Day : $Month : $Year"
        activityCalendar.getUser(userCalendarAdapter, Date)
    }

}