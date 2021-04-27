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
import com.example.myroom.activitycalendar.model.TimeServer
import com.example.myroom.activitycalendar.model.UserCalendar
import com.example.myroom.activitycalendar.rcvadapter.UserCalendarAdapter
import com.example.myroom.activitylistmem.model.WorkFlow
import com.example.myroom.activitymain.MainActivity
import com.google.firebase.database.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.text.SimpleDateFormat
import java.util.*

class CalendarFragment : Fragment() {

    private lateinit var userCalendarAdapter: UserCalendarAdapter
    private lateinit var handler: Handler
    private lateinit var activityCalendar: ActivityCalendar
    private lateinit var databaseReference: DatabaseReference

    //    private lateinit var listUser: MutableList<UserCalendar>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val calendarView = inflater.inflate(R.layout.fragment_calendar, container, false)
        val calendar: CalendarView = calendarView.findViewById(R.id.calender_view)
        val recyclerView: RecyclerView = calendarView.findViewById(R.id.rcv_user_calendar)
        activityCalendar = activity as ActivityCalendar
        databaseReference = FirebaseDatabase.getInstance().reference

        userCalendarAdapter = UserCalendarAdapter(requireContext())
//        listUser = mutableListOf()
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
        var Month = ""
        Month = if (month + 1 < 10) {
            "0" + (month + 1).toString()
        } else {
            (month + 1).toString()
        }
        val Year: String = year.toString()
        val Date = "$Day : $Month : $Year"
        getUser(Date)
    }

    private fun getUser(date: String) {
//        val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference
        databaseReference.child(MainActivity.PARENT_DAY_CHILD).child(date).addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val listId: MutableList<IDCalendar> = mutableListOf()
                    if (snapshot.hasChildren()) {
                        for (dataSnapshot: DataSnapshot in snapshot.children) {
                            /* get list ID */
                            listId.add(
                                IDCalendar(
                                    dataSnapshot.key!!,
                                    date
                                )
                            )
                        }
                    }
                    /* find user by id and set data */
                    var listUser: MutableList<UserCalendar> = mutableListOf()
                    val findUser = GlobalScope.async {
                        listUser = findUserByID(listId)
                        userCalendarAdapter.setData(listUser)
                    }
                    findUser.start()
                    /* **************** */
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
    }

    private fun findUserByID(listId: MutableList<IDCalendar>): MutableList<UserCalendar> {
//        val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference
        val listUser: MutableList<UserCalendar> = mutableListOf()
        var workFlow: WorkFlow = WorkFlow("0", "0", "0")
        for (idCalendar: IDCalendar in listId) {
            /* get user with each id */
            databaseReference.child(MainActivity.PARENT_CHILD).child(idCalendar.ID)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val checkUser: Boolean = snapshot.hasChild(MainActivity.NAME_CHILD) &&
                                snapshot.hasChild(MainActivity.RANK_CHILD) &&
                                snapshot.hasChild(MainActivity.ROOM_CHILD)
                        if (checkUser) {
                            val name =
                                snapshot.child(MainActivity.NAME_CHILD).value.toString() // name
                            if (snapshot.hasChild(MainActivity.WORK_TIME_CHILD)) {              // get time
                                /* have time start - stop */
                                val listTime: MutableList<TimeServer> = mutableListOf()
                                /* get minute */
                                for (snapshot1: DataSnapshot in snapshot.child(MainActivity.WORK_TIME_CHILD)
                                    .child(idCalendar.Time).children) {
                                    val timeServer: TimeServer = TimeServer(
                                        snapshot1.key.toString()
                                    )
                                    listTime.add(timeServer)
                                }
                                workFlow = WorkFlow(
                                    idCalendar.Time,
                                    listTime[0].Time,
                                    listTime[listTime.size - 1].Time
                                )

                                listUser.add(
                                    UserCalendar(
                                        idCalendar.ID,
                                        name,
                                        workFlow.getStart(),
                                        workFlow.getEnd()
                                    )
                                )
                            } else {
                                /* no have start - stop */
                                listUser.add(
                                    UserCalendar(
                                        idCalendar.ID,
                                        name,
                                        "0",
                                        "0"
                                    )
                                )
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })
        }
//        userCalendarAdapter.setData(listUser)
        return listUser
    }

    private fun getTime(): String {
        val currentDate = SimpleDateFormat("dd : MM : yyyy", Locale.getDefault()).format(Date())
        val currentTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
        return currentDate
    }

    private fun getTimeWork(id: String, time: String): WorkFlow {
//        val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference
        var workFlow: WorkFlow = WorkFlow("0", "0", "0")
        databaseReference.child(MainActivity.PARENT_CHILD).child(id)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot1: DataSnapshot) {
                    if (snapshot1.hasChild(MainActivity.WORK_TIME_CHILD)) {
                        databaseReference.child(MainActivity.PARENT_CHILD).child(id)
                            .child(MainActivity.WORK_TIME_CHILD)
                            .child(time)
                            .addValueEventListener(object : ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {

                                    val timeLine: MutableList<Int> = mutableListOf()
                                    for (dataSnapshot1: DataSnapshot in snapshot.children) {
                                        // dataSnapshot1= time in-out
                                        timeLine.add(dataSnapshot1.key?.toInt()!!)
                                    }
                                    workFlow = WorkFlow(
                                        snapshot.key!!,
                                        timeLine[0].toString(),
                                        timeLine[timeLine.size - 1].toString()
                                    )
                                    /* set data time work */

                                }

                                override fun onCancelled(error: DatabaseError) {
                                    TODO("Not yet implemented")
                                }

                            })

                    } else {
                        /* no data */
                        workFlow = WorkFlow("0", "0", "0")
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
        return workFlow
    }
}