package com.example.myroom.activitysummary.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myroom.R
import com.example.myroom.activitysummary.ActivitySummary
import com.example.myroom.activitysummary.rcvadapter.RcvUserMonthAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UserInMonthFragment : Fragment() {

    private lateinit var databaseReference: DatabaseReference
    private lateinit var activitySummary: ActivitySummary
    private lateinit var month: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var rcvUserMonthAdapter: RcvUserMonthAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val userMonthView = inflater.inflate(R.layout.fragment_user_in_month, container, false)
        databaseReference = FirebaseDatabase.getInstance().reference
        activitySummary = activity as ActivitySummary
        month = userMonthView.findViewById(R.id.month_in_user_frag)
        recyclerView = userMonthView.findViewById(R.id.rcv_user_in_month)
        rcvUserMonthAdapter = RcvUserMonthAdapter(requireContext())

        val linearLayoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = rcvUserMonthAdapter
        val divider: RecyclerView.ItemDecoration =
            DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        recyclerView.addItemDecoration(divider)

        val bundle: Bundle? = arguments
        bundle?.let {
            val Month: String? = bundle.getString(ActivitySummary.KEY_MONTH)
            month.text = Month
            activitySummary.findUserInMonth(rcvUserMonthAdapter, Month)
        }

        return userMonthView
    }


}