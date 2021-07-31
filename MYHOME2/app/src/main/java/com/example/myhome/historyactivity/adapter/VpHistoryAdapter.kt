package com.example.myhome.historyactivity.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myhome.historyactivity.fragment.CalendarDhtHistoryFragment
import com.example.myhome.historyactivity.fragment.HistoryDhtFragment

class VpHistoryAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CalendarDhtHistoryFragment()
            1 -> HistoryDhtFragment()
            else -> {
                HistoryDhtFragment()
            }
        }
    }

}