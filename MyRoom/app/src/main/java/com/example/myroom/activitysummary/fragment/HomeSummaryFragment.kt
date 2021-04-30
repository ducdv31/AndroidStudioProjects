package com.example.myroom.activitysummary.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myroom.R
import com.example.myroom.activitysummary.ActivitySummary
import com.example.myroom.activitysummary.rcvadapter.MonthAdapter

class HomeSummaryFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var monthAdapter: MonthAdapter
    private lateinit var activitySummary: ActivitySummary

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val homeView = inflater.inflate(R.layout.fragment_home_summary, container, false)
        recyclerView = homeView.findViewById(R.id.rcv_month_select)
        activitySummary = activity as ActivitySummary

        monthAdapter = MonthAdapter(requireContext(), object : MonthAdapter.IClickUserInMonth {
            override fun onClickUser(month: String) {
                activitySummary.gotoUserInMonthFragment(month)
            }

        })
        val linearLayoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        val gridLayoutManager = GridLayoutManager(
            requireContext(),
            2,
            RecyclerView.VERTICAL,
            false
        )

        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter = monthAdapter
        val itemDecoration: RecyclerView.ItemDecoration =
            DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
//        recyclerView.addItemDecoration(itemDecoration)

        val month: MutableList<Int> = mutableListOf()
        for (i: Int in 1..12) {
            month.add(i)
        }

        monthAdapter.setData(month)

        return homeView
    }

}