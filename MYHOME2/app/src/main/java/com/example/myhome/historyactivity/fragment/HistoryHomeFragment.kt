package com.example.myhome.historyactivity.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.myhome.R
import com.example.myhome.historyactivity.HistoryActivity
import com.example.myhome.historyactivity.adapter.VpHistoryAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HistoryHomeFragment : Fragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var historyActivity: HistoryActivity
    private lateinit var vpHistoryAdapter: VpHistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragView = inflater.inflate(R.layout.fragment_history_home, container, false)
        initVar(fragView)
        viewPager.adapter = vpHistoryAdapter
        viewPager.offscreenPageLimit = 3
        TabLayoutMediator(
            tabLayout,
            viewPager
        ) { tab, position ->
            when (position) {
                0 -> tab.text = "Date"
                1 -> tab.text = "List Date"
            }
        }.attach()

        return fragView
    }

    private fun initVar(fragView: View) {
        viewPager = fragView.findViewById(R.id.vpg_history)
        tabLayout = fragView.findViewById(R.id.tab_history)
        historyActivity = activity as HistoryActivity
        vpHistoryAdapter = VpHistoryAdapter(this)
    }
}