package com.example.myroom.activitylistmem.knowuser

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myroom.R
import com.example.myroom.activitylistmem.ActivityListMem
import com.example.myroom.activitylistmem.knowuser.rcvadapter.RcvTimeAdapter
import com.example.myroom.activitylistmem.model.UserDetail
import com.example.myroom.activitylistmem.model.WorkFlow
import com.example.myroom.activitymain.MainActivity
import com.example.myroom.timeconverter.TimeConverter2
import com.google.firebase.database.*

class DetailUserFragment : Fragment() {

    private lateinit var rcvTimeAdapter: RcvTimeAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var activityListMem: ActivityListMem
    private lateinit var myToast:Toast
    @SuppressLint("ShowToast")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val detailView = inflater
            .inflate(R.layout.fragment_detail_user, container, false)
        val name: TextView = detailView.findViewById(R.id.name_user_detail)
        val rank: TextView = detailView.findViewById(R.id.rank_user_detail)
        val room: TextView = detailView.findViewById(R.id.room_user_detail)
        val time: TextView = detailView.findViewById(R.id.all_tim_detail_user)
        var minutes : Int = 0
        activityListMem = activity as ActivityListMem
        recyclerView = detailView.findViewById(R.id.rcv_user_work_time)
        rcvTimeAdapter = RcvTimeAdapter(requireContext(), object : RcvTimeAdapter.ISummaryUser{
            override fun onReceivedSummary(minute: Int) {
                minutes += minute
                Log.e("Time", minutes.toString())
                time.text = TimeConverter2.convertFromMinutesWithDay(minutes)
            }

        })
        val linearLayoutManager: LinearLayoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = rcvTimeAdapter
        val itemDecoration: RecyclerView.ItemDecoration =
            DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
//        recyclerView.addItemDecoration(itemDecoration)
        myToast = Toast.makeText(requireContext(), "No data", Toast.LENGTH_LONG)

        val bundle: Bundle? = arguments
        bundle?.let {
            val userDetail: UserDetail = bundle.get(ActivityListMem.USER_SEND_TAG) as UserDetail
            name.text = userDetail.name
            rank.text = userDetail.rank.toString()
            room.text = userDetail.room
            userDetail.id?.let { getTimeWork(userDetail.id) }
        }


        return detailView
    }

    private fun getTimeWork(id: String) {
        val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference
        databaseReference.child(MainActivity.PARENT_CHILD).child(id)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot1: DataSnapshot) {
                    if (snapshot1.hasChild(MainActivity.WORK_TIME_CHILD)) {

                        databaseReference.child(MainActivity.PARENT_CHILD).child(id)
                            .child(MainActivity.WORK_TIME_CHILD)
                            .addValueEventListener(object : ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    if (snapshot.hasChildren()) {
                                        val workFlow: MutableList<WorkFlow> = mutableListOf()
                                        for (dataSnapshot: DataSnapshot in snapshot.children) {//dataSnapshot = day
                                            if (dataSnapshot.hasChildren()) {
                                                val timeLine: MutableList<Int> = mutableListOf()
                                                for (dataSnapshot1: DataSnapshot in dataSnapshot.children) { // dataSnapshot1= time in-out
                                                    timeLine.add(dataSnapshot1.key?.toInt()!!)
                                                }
                                                workFlow.add(
                                                    WorkFlow(
                                                        dataSnapshot.key!!,
                                                        timeLine[0].toString(),
                                                        timeLine[timeLine.size - 1].toString()
                                                    )
                                                )
                                            }

                                        }
                                        /* set data time work */
                                        rcvTimeAdapter.setData(workFlow)
                                    }
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    TODO("Not yet implemented")
                                }

                            })

                    } else {
                        myToast.show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
    }

    override fun onPause() {
        super.onPause()
        myToast.cancel()
    }
}