package com.example.myroom.activitylistmem.knowuser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.myroom.R
import com.example.myroom.activitylistmem.ActivityListMem
import com.example.myroom.activitylistmem.model.UserDetail
import com.example.myroom.activitymain.MainActivity
import com.google.firebase.database.FirebaseDatabase

class UpdateUserFragment : Fragment() {

    private var name: EditText? = null
    private var room: EditText? = null
    private var rank: EditText? = null
    private var id: TextView? = null
    private var activityListMem: ActivityListMem? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val updateView = inflater
            .inflate(R.layout.fragment_update_user, container, false)
        id = updateView.findViewById(R.id.id_in_update_data_fragment)
        name = updateView.findViewById(R.id.name_update)
        rank = updateView.findViewById(R.id.rank_update)
        room = updateView.findViewById(R.id.room_update)
        val update: Button = updateView.findViewById(R.id.update_detail_user)
        activityListMem = activity as ActivityListMem


        val bundle: Bundle? = arguments
        bundle?.let {
            val userDetail: UserDetail =
                bundle.get(ActivityListMem.USER_SEND_TO_UPDATE) as UserDetail
            id?.text = userDetail.id
            name?.setText(userDetail.name)
            rank?.setText(userDetail.rank.toString())
            room?.setText(userDetail.room)
        }
        update.setOnClickListener(View.OnClickListener {
            val Name = name?.text.toString()
            val Rank = rank?.text.toString()
            val Room = room?.text.toString()
            val ID = id?.text.toString()
            updateUser(ID, Name, Rank.toInt(), Room)
            activityListMem!!.closeKeyboard()
        })

        return updateView
    }

    private fun updateUser(
        id: String? = null,
        name: String? = null,
        rank: Int? = null,
        room: String? = null
    ) {
        val databaseReference = FirebaseDatabase.getInstance().reference
        databaseReference.child(MainActivity.PARENT_CHILD).child(id!!)
            .child(MainActivity.NAME_CHILD).setValue(name)
        databaseReference.child(MainActivity.PARENT_CHILD).child(id).child(MainActivity.RANK_CHILD)
            .setValue(rank)
        databaseReference.child(MainActivity.PARENT_CHILD).child(id).child(MainActivity.ROOM_CHILD)
            .setValue(room)
    }

}