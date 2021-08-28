package com.example.myhome.ui.adapter.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myhome.ui.view.fragment.account.AccountFragment
import com.example.myhome.ui.view.fragment.googlemap.GoogleMapFragment
import com.example.myhome.ui.view.fragment.main.HomeDataFragment

class HomeViewPager(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeDataFragment()
            1 -> AccountFragment()
            else -> HomeDataFragment()
        }
    }
}