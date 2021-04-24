package com.example.myroom.activity2addmem.unknowuser

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myroom.R
import com.example.myroom.activity2addmem.ActivityAddMem
import com.example.myroom.activity2addmem.unknowuser.model.UserID
import com.example.myroom.activitymain.MainActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SetUserFragment : Fragment() {

    @SuppressLint("ShowToast")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val setUserView = inflater.inflate(R.layout.fragment_set_user, container, false)
        val id: TextView = setUserView.findViewById(R.id.id_in_set_data_fragment)
        val name: EditText = setUserView.findViewById(R.id.name_set)
        val rank: EditText = setUserView.findViewById(R.id.rank_set)
        val room: EditText = setUserView.findViewById(R.id.room_set)
        val setUser: Button = setUserView.findViewById(R.id.set_detail_user)
        val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference
        var ID: String = ""

        val bundle: Bundle? = arguments
        bundle?.let {
            val userID: UserID = bundle.get(ActivityAddMem.DATA_SEND_TO_SET_USER) as UserID
            userID.let {
                id.text = userID.id
                ID = userID.id
            }
        }

        setUser.setOnClickListener(View.OnClickListener {
            val Name: String = name.text.toString()
            val Rank: String = rank.text.toString()
            val Room: String = room.text.toString()
            if (Name.isEmpty()) {
                Toast.makeText(requireContext(), "Name is invalid", Toast.LENGTH_SHORT).show()
            } else if (Rank.isEmpty()) {
                Toast.makeText(requireContext(), "Rank is invalid", Toast.LENGTH_SHORT).show()
            } else if (Room.isEmpty()) {
                Toast.makeText(requireContext(), "Room is invalid", Toast.LENGTH_SHORT).show()
            } else {
                databaseReference.child(MainActivity.PARENT_CHILD).child(ID)
                    .child(MainActivity.NAME_CHILD).setValue(Name)
                databaseReference.child(MainActivity.PARENT_CHILD).child(ID)
                    .child(MainActivity.RANK_CHILD).setValue(Rank.toInt())
                databaseReference.child(MainActivity.PARENT_CHILD).child(ID)
                    .child(MainActivity.ROOM_CHILD).setValue(Room)
            }
        })

        return setUserView
    }

}