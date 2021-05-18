package com.example.myroom.activitycalendar

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.myroom.R
import com.example.myroom.activitycalendar.fragment.CalendarFragment
import com.example.myroom.activitycalendar.fragment.TaskUserFragment
import com.example.myroom.activitycalendar.model.TaskUser
import com.example.myroom.activitycalendar.model.TimeServer
import com.example.myroom.activitycalendar.model.UserCalendar
import com.example.myroom.activitycalendar.rcvadapter.UserCalendarAdapter
import com.example.myroom.activitylistmem.model.WorkFlow
import com.example.myroom.activitymain.MainActivity
import com.example.myroom.components.`interface`.IPermissionRequest
import com.google.firebase.database.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.text.SimpleDateFormat
import java.util.*

class ActivityCalendar : AppCompatActivity(), IPermissionRequest {

    companion object {
        const val TAG_TASKUSER: String = "task user send to task fragment"
        const val TAG_DATE: String = "date task send to task fragment"
        const val TAG_BACKSTACK_CALENDAR: String = "add calendar fragment to backstack"
    }

    var actionBar: ActionBar? = null
    private lateinit var databaseReference: DatabaseReference
    val scope = CoroutineScope(Job() + Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        actionBar = supportActionBar
        actionBar?.title = "Day of work"
        actionBar?.setDisplayHomeAsUpEnabled(true)

        databaseReference = FirebaseDatabase.getInstance().reference

        val fragmentTransition: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.frame_calendar, CalendarFragment())
        fragmentTransition.commit()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    fun gotoTaskUserFragment(taskUser: TaskUser, date: String) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val taskUserFragment = TaskUserFragment()

        val bundle = Bundle()
        bundle.putSerializable(TAG_TASKUSER, taskUser)
        bundle.putString(TAG_DATE, date)
        taskUserFragment.arguments = bundle

        fragmentTransaction.replace(R.id.frame_calendar, taskUserFragment)
        fragmentTransaction.addToBackStack(TAG_BACKSTACK_CALENDAR)
        fragmentTransaction.commit()

    }

    fun getUser(userCalendarAdapter: UserCalendarAdapter, date: String) {
//        Log.e("Date", date)
        val listUser: MutableList<UserCalendar> = mutableListOf()
        var workFlow: WorkFlow = WorkFlow("0", "0", "0")
        FirebaseDatabase.getInstance().reference
            .child(MainActivity.PARENT_DAY_CHILD).child(date)
            .addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(snapshot1: DataSnapshot) {
                        if (snapshot1.hasChildren()) {
                            for (dataSnapshot: DataSnapshot in snapshot1.children) {
                                /* get ID */
                                val id = dataSnapshot.key.toString()
//                                Log.e("Id calendar", id)
                                /* find user */
                                FirebaseDatabase.getInstance().reference
                                    .child(MainActivity.PARENT_CHILD)
                                    .child(id)
                                    .addValueEventListener(object : ValueEventListener {
                                        override fun onDataChange(snapshot: DataSnapshot) {
                                            val checkUser: Boolean =
                                                snapshot.hasChild(MainActivity.NAME_CHILD) &&
                                                        snapshot.hasChild(MainActivity.RANK_CHILD) &&
                                                        snapshot.hasChild(MainActivity.ROOM_CHILD)
                                            if (checkUser) {
                                                val name =
                                                    snapshot.child(MainActivity.NAME_CHILD).value.toString() // name

                                                if (snapshot.hasChild(MainActivity.WORK_TIME_CHILD)) {              // get time
                                                    /* have time start - stop */
                                                    val listTime: MutableList<TimeServer> =
                                                        mutableListOf()
                                                    /* get minute */
                                                    for (snapshot1: DataSnapshot in snapshot.child(
                                                        MainActivity.WORK_TIME_CHILD
                                                    ).child(date).children) {
                                                        val timeServer: TimeServer = TimeServer(
                                                            snapshot1.key.toString()
                                                        )
                                                        listTime.add(timeServer)
                                                    }
                                                    workFlow = WorkFlow(
                                                        date,
                                                        listTime[0].Time,
                                                        listTime[listTime.size - 1].Time
                                                    )

                                                    listUser.add(
                                                        UserCalendar(
                                                            id,
                                                            name,
                                                            workFlow.getStart(),
                                                            workFlow.getEnd()
                                                        )
                                                    )
                                                } else {
                                                    /* no have start - stop */
                                                    listUser.add(
                                                        UserCalendar(
                                                            date,
                                                            name,
                                                            "0",
                                                            "0"
                                                        )
                                                    )
                                                }

                                            }
                                            userCalendarAdapter.setData(listUser)
                                        }

                                        override fun onCancelled(error: DatabaseError) {

                                        }

                                    })
                            }
                        } else {
                            Log.e("ID calendar", "No data")
                        }
                        /* find user by id and set data */
                        /* **************** */
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })
        userCalendarAdapter.setData(listUser)
    }

    fun getTime(): String {
        val currentDate = SimpleDateFormat("dd : MM : yyyy", Locale.getDefault()).format(Date())
        val currentTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
        val strDate = currentDate.split(":")
        val day = strDate[0].trim().toInt()
        val month = strDate[1].trim()
        val year = strDate[2].trim()

        return "$day : $month : $year"
    }

    override fun hasUnKnowUser(has: Boolean) {
        if (has){
            TaskUserFragment.fabAddTask?.visibility = View.VISIBLE
            TaskUserFragment.swipeAble = true
        }
    }

    override fun hasUserInRoom(hasInRoom: Boolean, username: String) {
        if (hasInRoom){
            TaskUserFragment.fabAddTask?.visibility = View.VISIBLE
            TaskUserFragment.swipeAble = true
        }
    }

    override fun hasRootUser(hasRoot: Boolean) {
        if (hasRoot){
            TaskUserFragment.fabAddTask?.visibility = View.VISIBLE
            TaskUserFragment.swipeAble = true
        }
    }

    override fun hasSupperRoot(hasSupperRoot: Boolean) {
        if (hasSupperRoot){
            TaskUserFragment.fabAddTask?.visibility = View.VISIBLE
            TaskUserFragment.swipeAble = true
        }
    }
}