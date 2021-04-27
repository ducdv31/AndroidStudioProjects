package com.example.myroom.activitysummary

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.myroom.R
import com.example.myroom.activitymain.MainActivity
import com.example.myroom.activitysummary.fragment.HomeSummaryFragment
import com.example.myroom.activitysummary.fragment.UserInMonthFragment
import com.example.myroom.activitysummary.model.IDSummary
import com.example.myroom.activitysummary.model.UserSummary
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

    fun findUserInMonth(month: String?) {
        val date: String = "00 : $month : 2021"
        /* get date from server and pick up the month to compare */
        databaseReference.child(MainActivity.PARENT_MONTH_CHILD)
            .child(month!!)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.hasChildren()) {
                        val listID: MutableList<IDSummary> = mutableListOf()
                        for (snapshot1: DataSnapshot in snapshot.children) { // snapshot1 = day
                            Log.e("Id in month", snapshot1.key.toString())
                            /* compare month with id and get time */
//                            listID.add(IDSummary(snapshot1.key.toString()))
                            FirebaseDatabase.getInstance().reference.child(MainActivity.PARENT_CHILD)
                                .child(snapshot1.key.toString())
                                .addValueEventListener(object : ValueEventListener {
                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        val checkUser: Boolean =
                                            snapshot.hasChild(MainActivity.NAME_CHILD) &&
                                                    snapshot.hasChild(MainActivity.RANK_CHILD) &&
                                                    snapshot.hasChild(MainActivity.ROOM_CHILD)
                                        if (checkUser) {
                                            val name =
                                                snapshot.child(MainActivity.NAME_CHILD).value.toString() // name
                                            Log.e("Name", name)
                                        }
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
                            listUser.add(UserSummary(name))
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