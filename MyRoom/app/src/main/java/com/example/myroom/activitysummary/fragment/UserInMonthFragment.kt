package com.example.myroom.activitysummary.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myroom.R
import com.example.myroom.activitymain.MainActivity
import com.example.myroom.activitysummary.ActivitySummary
import com.google.firebase.database.*
import kotlinx.coroutines.MainScope

class UserInMonthFragment : Fragment() {

    private lateinit var databaseReference: DatabaseReference
    private lateinit var activitySummary: ActivitySummary

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val userMonthView = inflater.inflate(R.layout.fragment_user_in_month, container, false)
        databaseReference= FirebaseDatabase.getInstance().reference
        activitySummary = activity as ActivitySummary

        val bundle: Bundle? = arguments
        bundle?.let {
            val month: String? = bundle.getString(ActivitySummary.KEY_MONTH)
            activitySummary.findUserInMonth(month)
        }

        return userMonthView
    }


}