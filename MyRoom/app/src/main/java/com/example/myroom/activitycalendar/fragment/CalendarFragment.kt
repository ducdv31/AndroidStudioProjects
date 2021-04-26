package com.example.myroom.activitycalendar.fragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myroom.R
import com.example.myroom.activitycalendar.ActivityCalendar
import com.example.myroom.activitycalendar.model.IDCalendar
import com.example.myroom.activitycalendar.model.UserCalendar
import com.example.myroom.activitycalendar.rcvadapter.UserCalendarAdapter
import com.example.myroom.activitymain.MainActivity
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*

class CalendarFragment : Fragment() {

    private lateinit var userCalendarAdapter: UserCalendarAdapter
    private lateinit var handler: Handler
    private lateinit var activityCalendar: ActivityCalendar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val calendarView = inflater.inflate(R.layout.fragment_calendar, container, false)
        val calendar: CalendarView = calendarView.findViewById(R.id.calender_view)
        val recyclerView: RecyclerView = calendarView.findViewById(R.id.rcv_user_calendar)
        activityCalendar = activity as ActivityCalendar

        userCalendarAdapter = UserCalendarAdapter(requireContext())

        val linearLayoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)


        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = userCalendarAdapter
        val itemDecoration: RecyclerView.ItemDecoration =
            DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        recyclerView.addItemDecoration(itemDecoration)

        calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            /* get time and set data to list user */
            getUserFromDay(dayOfMonth, month, year)
        }

        getUser(getTime())

        return calendarView
    }

    private fun getUserFromDay(dayOfMonth: Int, month: Int, year: Int) {
        val Day: String = dayOfMonth.toString()
        var Month: String = ""
        if (month + 1 < 10) {
            Month = "0" + (month + 1).toString()
        } else {
            Month = (month + 1).toString()
        }
        val Year: String = year.toString()
        val Date = "$Day : $Month : $Year"
        getUser(Date)
    }

    private fun getUser(date: String) {
        val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference
        databaseReference.child(MainActivity.PARENT_DAY_CHILD).child(date).addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val listId: MutableList<IDCalendar> = mutableListOf()
                    if (snapshot.hasChildren()) {
                        for (dataSnapshot: DataSnapshot in snapshot.children) {
                            /* get list ID */
                            listId.add(IDCalendar(dataSnapshot.key!!))
                        }
                    }
                    /* find user by id and set data */
                    findUserByID(listId)
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
    }

    private fun findUserByID(listId: MutableList<IDCalendar>) {
        val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference
        val listUser: MutableList<UserCalendar> = mutableListOf()
        for (idCalendar: IDCalendar in listId) {
            /* get user with each id */
            databaseReference.child(MainActivity.PARENT_CHILD).child(idCalendar.ID)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val checkUser: Boolean = snapshot.hasChild(MainActivity.NAME_CHILD) &&
                                snapshot.hasChild(MainActivity.RANK_CHILD) &&
                                snapshot.hasChild(MainActivity.ROOM_CHILD)
                        if (checkUser) {
                            val Name = snapshot.child(MainActivity.NAME_CHILD).value.toString()
                            listUser.add(UserCalendar(idCalendar.ID, Name))
//                            userCalendarAdapter?.setData(listUser)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })
            userCalendarAdapter.setData(listUser)
        }
//        userCalendarAdapter?.setData(listUser)

    }

    private fun getTime(): String {
        val currentDate = SimpleDateFormat("dd : MM : yyyy", Locale.getDefault()).format(Date())
        val currentTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
        return currentDate
    }
}