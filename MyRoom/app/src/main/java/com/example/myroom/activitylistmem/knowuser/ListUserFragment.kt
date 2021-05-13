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
import com.chauthai.swipereveallayout.SwipeRevealLayout
import com.example.myroom.R
import com.example.myroom.activitylistmem.ActivityListMem
import com.example.myroom.activitylistmem.knowuser.rcvadapter.RcvUserAdapter
import com.example.myroom.activitylistmem.model.UserDetail
import com.example.myroom.activitylistmem.model.UserNID
import com.example.myroom.activitymain.MainActivity
import com.example.myroom.activitymain.MyApplication
import com.example.myroom.components.dialog.TFDialog
import com.google.firebase.database.*

class ListUserFragment : Fragment() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var rcvUserAdapter: RcvUserAdapter

        @Volatile
        var swipeAble: Boolean = false
        @Volatile
        var clickAble: Boolean = false
    }

    private val TAG_DIALOG_DELETE: String = "Delete user dialog list user"
    private lateinit var recyclerView: RecyclerView
//    private lateinit var rcvUserAdapter: RcvUserAdapter
    private lateinit var activityListMem: ActivityListMem
    private lateinit var myToast: Toast
    private lateinit var tfDialog: TFDialog
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

        tfDialog = TFDialog(activityListMem, object : TFDialog.IDialogResponse {
            override fun onDialogResponse(response: Boolean) {
                if (response) {
                    val bundle: Bundle? = tfDialog.arguments
                    bundle?.let {
                        val userDetail:UserDetail = bundle.get(TAG_DIALOG_DELETE) as UserDetail
                        FirebaseDatabase.getInstance().reference
                            .child(MainActivity.PARENT_CHILD)
                            .child(userDetail.id!!).setValue(null)
                    }
                }
            }

        })

        rcvUserAdapter =
            RcvUserAdapter(requireContext(), iClickUser = object : RcvUserAdapter.IClickUser {
                override fun onClickUser(userDetail: UserDetail) {
                    activityListMem.gotoDetailUser(userDetail)
                }

                override fun onClickUpdateUser(userDetail: UserDetail) {
                    activityListMem.gotoUpdateUser(userDetail)
                }

                override fun onClickDeleteUser(userDetail: UserDetail) {
                    val bundle = Bundle()
                    bundle.putSerializable(TAG_DIALOG_DELETE, userDetail)
                    tfDialog.arguments = bundle
                    tfDialog.show(requireActivity().supportFragmentManager, "Delete user")
                }

                override fun onSwipeRevealLayout(swipeRevealLayout: SwipeRevealLayout) {
                    if (swipeAble) {
                        swipeRevealLayout.setLockDrag(false)
                    } else {
                        swipeRevealLayout.setLockDrag(true)
                    }
                }

            })
        activityListMem.menuItem?.isVisible = true
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = rcvUserAdapter

        MyApplication.getUIDPermission(activityListMem)
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

    override fun onResume() {
        super.onResume()
        activityListMem.menuItem?.isVisible = true
        MyApplication.getUIDPermission(activityListMem)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        activityListMem.menuItem?.isVisible = true
    }

    override fun onPause() {
        super.onPause()
        myToast.cancel()
        activityListMem.closeKeyboard()
        if (!activityListMem.searchView.isIconified){
            activityListMem.searchView.isIconified = true
        }
        activityListMem.menuItem?.isVisible = false
    }
}