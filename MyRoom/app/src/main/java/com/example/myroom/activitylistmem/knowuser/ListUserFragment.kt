package com.example.myroom.activitylistmem.knowuser

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myroom.R
import com.example.myroom.activitylistmem.ActivityListMem
import com.example.myroom.activitylistmem.knowuser.rcvadapter.RcvUserAdapter
import com.example.myroom.activitylistmem.model.UserDetail
import com.example.myroom.activitylistmem.model.UserNID
import com.example.myroom.activitymain.MainActivity
import com.google.firebase.database.*

class ListUserFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var rcvUserAdapter: RcvUserAdapter
    private lateinit var activityListMem: ActivityListMem
    private lateinit var myToast: Toast

    @SuppressLint("ShowToast")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val listUserView = inflater
            .inflate(R.layout.fragment_list_user, container, false)
        recyclerView = listUserView.findViewById(R.id.rcv_list_mem)
        activityListMem = activity as ActivityListMem
        myToast = Toast.makeText(activityListMem, "No user", Toast.LENGTH_LONG)

        val linearLayoutManager = LinearLayoutManager(activityListMem, RecyclerView.VERTICAL, false)
        rcvUserAdapter =
            RcvUserAdapter(requireContext(), iClickUser = object : RcvUserAdapter.IClickUser {
                override fun onClickUser(userDetail: UserDetail) {
                    activityListMem.gotoDetailUser(userDetail)
                }

                override fun onClickUpdateUser(userDetail: UserDetail) {
                    activityListMem.gotoUpdateUser(userDetail)
                }

                override fun onClickDeleteUser(userDetail: UserDetail) {

                }

            })

        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = rcvUserAdapter

        getListData()

        return listUserView
    }

    private fun getListData() {
        val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference
        databaseReference.child(MainActivity.PARENT_CHILD)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) { // snapshot = Deviot
                    if (snapshot.hasChildren()) {
                        val listMem: MutableList<UserDetail> = mutableListOf()
                        for (dataSnapshot1: DataSnapshot in snapshot.children) { //dataSnapshot1 = id
                            if (dataSnapshot1.hasChildren()) {
                                if (dataSnapshot1.hasChild(MainActivity.NAME_CHILD) &&
                                    dataSnapshot1.hasChild(MainActivity.RANK_CHILD) &&
                                    dataSnapshot1.hasChild(MainActivity.ROOM_CHILD)
                                ) {
                                    val userNID: UserNID? =
                                        dataSnapshot1.getValue(UserNID::class.java)
                                    listMem.add(
                                        UserDetail(
                                            dataSnapshot1.key!!,
                                            userNID!!.name,
                                            userNID.rank,
                                            userNID.room
                                        )
                                    )
                                }

                            }
                        }
                        rcvUserAdapter.setData(listMem)
                    } else {
                        myToast.show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("List user fragment", "onCancelled: Can't get data")
                }

            })

    }

    override fun onPause() {
        super.onPause()
        myToast.cancel()
    }
}