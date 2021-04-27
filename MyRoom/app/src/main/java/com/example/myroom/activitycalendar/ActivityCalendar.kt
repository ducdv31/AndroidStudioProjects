package com.example.myroom.activitycalendar

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.myroom.R
import com.example.myroom.activitycalendar.fragment.CalendarFragment
import com.example.myroom.activitycalendar.model.IDCalendar
import com.example.myroom.activitycalendar.model.TimeServer
import com.example.myroom.activitycalendar.model.UserCalendar
import com.example.myroom.activitycalendar.rcvadapter.UserCalendarAdapter
import com.example.myroom.activitylistmem.model.WorkFlow
import com.example.myroom.activitymain.MainActivity
import com.google.firebase.database.*
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class ActivityCalendar : AppCompatActivity() {

    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference
    val scope = CoroutineScope(Job() + Dispatchers.Main)

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
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    fun getUser(userCalendarAdapter: UserCalendarAdapter, date: String) {
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
                    val findUser = GlobalScope.async{
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

    fun findUserByID(listId: MutableList<IDCalendar>): MutableList<UserCalendar> {
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

    fun getTime(): String {
        val currentDate = SimpleDateFormat("dd : MM : yyyy", Locale.getDefault()).format(Date())
        val currentTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
        return currentDate
    }
}