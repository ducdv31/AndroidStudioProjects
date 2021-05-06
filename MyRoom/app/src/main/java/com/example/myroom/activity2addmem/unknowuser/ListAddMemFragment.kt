package com.example.myroom.activity2addmem.unknowuser

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myroom.R
import com.example.myroom.activity2addmem.ActivityAddMem
import com.example.myroom.activity2addmem.unknowuser.model.UserID
import com.example.myroom.activity2addmem.unknowuser.rcvadapter.RcvAddMemAdapter
import com.example.myroom.activitymain.MainActivity
import com.example.myroom.dialog.TFDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*

class ListAddMemFragment : Fragment() {

    companion object {
        const val TAG_DELETE_DIALOG = "Delete user dialog"
    }

    private var recyclerView: RecyclerView? = null
    private var rcvAddMemAdapter: RcvAddMemAdapter? = null
    private var myToast: Toast? = null
    private var listMember: MutableList<UserID>? = null
    lateinit var tfDialog: TFDialog

    @SuppressLint("ShowToast")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val listAddView: View = inflater
            .inflate(R.layout.fragment_list_add_mem, container, false)
        recyclerView = listAddView.findViewById(R.id.rcv_list_id_member)
        myToast = Toast.makeText(requireContext(), "No user", Toast.LENGTH_LONG)
        val activityAddMem: ActivityAddMem = activity as ActivityAddMem
        val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference
        val fab_delete_all: FloatingActionButton =
            listAddView.findViewById(R.id.bt_clear_list_add_user)

        tfDialog = TFDialog(requireContext(), object : TFDialog.IDialogResponse {
            override fun onDialogResponse(response: Boolean) {
                if (response) {
                    val bundle: Bundle? = tfDialog.arguments
                    bundle?.let {
                        val userID: UserID = bundle.get(TAG_DELETE_DIALOG) as UserID
                        databaseReference.child(MainActivity.PARENT_CHILD).child(userID.id)
                            .setValue(null)
                    }
                }
            }

        })

        rcvAddMemAdapter =
            RcvAddMemAdapter(requireContext(), object : RcvAddMemAdapter.IClickUserAdd {
                override fun onClickUser(userID: UserID) {
                    activityAddMem.gotoSetUserFragment(userID)
                }

                override fun onClickDeleteUser(userID: UserID) {

                    val bundle: Bundle = Bundle()
                    bundle.putSerializable(TAG_DELETE_DIALOG, userID)
                    tfDialog.arguments = bundle
                    tfDialog.show(requireActivity().supportFragmentManager, "Delete user")
                }

            })
        val linearLayoutManager: LinearLayoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        recyclerView?.layoutManager = linearLayoutManager
        recyclerView?.adapter = rcvAddMemAdapter
        val itemDecoration: RecyclerView.ItemDecoration =
            DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
//        recyclerView?.addItemDecoration(itemDecoration)

        fab_delete_all.setOnClickListener(View.OnClickListener {
            listMember?.let {
                for (userID: UserID in listMember!!) {
                    databaseReference.child(MainActivity.PARENT_CHILD).child(userID.id)
                        .setValue(null)
                }
            }

        })

        getListData()

        return listAddView
    }

    private fun getListData() {
        val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference
        databaseReference.child(MainActivity.PARENT_CHILD)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) { // snapshot = Deviot
                    if (snapshot.hasChildren()) {
                        val listMem: MutableList<UserID> = mutableListOf()
                        for (dataSnapshot1: DataSnapshot in snapshot.children) { //dataSnapshot1 = id
                            if (dataSnapshot1.hasChildren()) {
                                if (!dataSnapshot1.hasChild(MainActivity.NAME_CHILD) ||
                                    !dataSnapshot1.hasChild(MainActivity.RANK_CHILD) ||
                                    !dataSnapshot1.hasChild(MainActivity.ROOM_CHILD)
                                ) {
                                    val id: String = dataSnapshot1.key!!
                                    listMem.add(UserID(id))

                                }

                            }
                        }
                        rcvAddMemAdapter!!.setData(listMem)
                        listMember = listMem
                    } else {
                        myToast?.show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("List user fragment", "onCancelled: Can't get data")
                }

            })

    }
}