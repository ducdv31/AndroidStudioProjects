package com.example.myroom.activitysummary

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.myroom.R
import com.example.myroom.activitylistmem.model.WorkFlow
import com.example.myroom.activitymain.MainActivity
import com.example.myroom.activitysummary.fragment.HomeSummaryFragment
import com.example.myroom.activitysummary.fragment.UserInMonthFragment
import com.example.myroom.activitysummary.model.IDSummary
import com.example.myroom.activitysummary.model.UserSummary
import com.example.myroom.activitysummary.rcvadapter.RcvUserMonthAdapter
import com.google.firebase.database.*

class ActivitySummary : AppCompatActivity() {

    companion object {
        const val KEY_MONTH = "Month"
        const val BACK_STACK_USER_MONTH_TO_HOME = "User to Home"
    }

    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.title = "Summary"
        actionBar?.setDisplayHomeAsUpEnabled(true)

        databaseReference = FirebaseDatabase.getInstance().reference

        val fragmentTransition: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.frame_summary, HomeSummaryFragment())
        fragmentTransition.commit()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    fun gotoUserInMonthFragment(month: String) {
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        val userInMonthFragment = UserInMonthFragment()

        val bundle: Bundle = Bundle()
        bundle.putString(KEY_MONTH, month)
        userInMonthFragment.arguments = bundle

        fragmentTransaction.replace(R.id.frame_summary, userInMonthFragment)
        fragmentTransaction.addToBackStack(BACK_STACK_USER_MONTH_TO_HOME)
        fragmentTransaction.commit()

    }

    fun findUserInMonth(rcvUserMonthAdapter: RcvUserMonthAdapter, month: String?) {
        val date: String = "00 : $month : 2021"
        /* get date from server and pick up the month to compare */
        val listUser: MutableList<UserSummary> = mutableListOf()
        var workFlow: WorkFlow
        var minutes: Int = 0
        var day: Int = 0
        databaseReference.child(MainActivity.PARENT_MONTH_CHILD)
            .child(month!!)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.hasChildren()) {
                        for (snapshot1: DataSnapshot in snapshot.children) { // snapshot1 = id
//                            Log.e("Id in month", snapshot1.key.toString())
                            /* compare month with id and get time */
                            minutes = 0
                            day = 0
                            workFlow = WorkFlow("0", "0", "0")
//                            listID.add(IDSummary(snapshot1.key.toString()))
                            FirebaseDatabase.getInstance().reference.child(MainActivity.PARENT_CHILD)
                                .child(snapshot1.key.toString()) // id
                                .addValueEventListener(object : ValueEventListener {
                                    override fun onDataChange(snapshot2: DataSnapshot) {
                                        val checkUser: Boolean =
                                            snapshot2.hasChild(MainActivity.NAME_CHILD) &&
                                                    snapshot2.hasChild(MainActivity.RANK_CHILD) &&
                                                    snapshot2.hasChild(MainActivity.ROOM_CHILD)
                                        if (checkUser) {
                                            /* get name */
                                            val name =
                                                snapshot2.child(MainActivity.NAME_CHILD).value.toString() // name
                                            // snapshot2 = id (Deviot)
//                                            Log.e("Name", name)
                                            /* get total time */
                                            if (snapshot2.hasChild(MainActivity.WORK_TIME_CHILD)) {
                                                minutes = 0
                                                day = 0
                                                workFlow = WorkFlow("0", "0", "0")
                                                for (snapShot1: DataSnapshot in snapshot2.child(
                                                    MainActivity.WORK_TIME_CHILD
                                                ).children) {
                                                    // snapShot1 = 25 : 04 : 2021
                                                    /* check for month */
                                                    val Date = snapShot1.key.toString()
                                                    val Month: String = Date.split(":")[1]
//                                                    Log.d("ID", snapshot1.key.toString())
//                                                    Log.d("Date", Date)
//                                                    Log.e("Month Server", Month)

                                                    if (Month.trim() == month.trim()) {
                                                        /* get start - stop -> total time */
                                                        val time1: MutableList<Int> =
                                                            mutableListOf()
                                                        for (timeSnapshot: DataSnapshot in snapShot1.children) {
                                                            time1.add(timeSnapshot.key?.toInt()!!)
                                                        }
//                                                        Log.e("Time", time1.toString())
                                                        workFlow = WorkFlow(
                                                            Date,
                                                            time1[0].toString(),
                                                            time1[time1.size - 1].toString()
                                                        )
                                                        /********************/
                                                        minutes += workFlow.getTimeLineMinutes()
                                                        day++
                                                    }

                                                    /* get start - end -> total time */

                                                    /* ok -> add UserSummary */
                                                }
                                            } else {
                                                minutes = 0
                                                day = 0
                                            }
                                            /* set list user */
//                                            Log.e("Time", minutes.toString())
                                            listUser.add(
                                                UserSummary(
                                                    name,
                                                    day.toString(),
                                                    minutes.toString()
                                                )
                                            )
                                        }
                                        listUser.sortBy { userSummary -> userSummary.AllTime }
                                        rcvUserMonthAdapter.setData(listUser)
                                    }

                                    override fun onCancelled(error: DatabaseError) {

                                    }

                                })
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
        rcvUserMonthAdapter.setData(listUser)
    }

    fun findUserByID(listId: MutableList<IDSummary>): MutableList<UserSummary> {
        val listUser: MutableList<UserSummary> = mutableListOf()
        for (idSummary: IDSummary in listId) {
            /* get user with each id */
            databaseReference.child(MainActivity.PARENT_CHILD).child(idSummary.ID)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val checkUser: Boolean = snapshot.hasChild(MainActivity.NAME_CHILD) &&
                                snapshot.hasChild(MainActivity.RANK_CHILD) &&
                                snapshot.hasChild(MainActivity.ROOM_CHILD)
                        if (checkUser) {
                            val name =
                                snapshot.child(MainActivity.NAME_CHILD).value.toString() // name
                            listUser.add(UserSummary(name, "10", "10"))
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })
        }
//        userCalendarAdapter.setData(listUser)
        return listUser
    }
}