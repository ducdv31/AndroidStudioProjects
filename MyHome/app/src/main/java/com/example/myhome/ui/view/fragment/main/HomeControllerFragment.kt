package com.example.myhome.ui.view.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.myhome.R
import com.example.myhome.ui.adapter.main.HomeViewPager
import com.example.myhome.ui.view.activity.main.MainActivity
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeControllerFragment : Fragment() {
    private lateinit var mainActivity: MainActivity
    private lateinit var homeViewPager: HomeViewPager
    private lateinit var view_pager_home: ViewPager2
    private lateinit var bottom_view_home: BottomNavigationView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragView = inflater.inflate(R.layout.fragment_home_controller, container, false)
        mainActivity = activity as MainActivity
        view_pager_home = fragView.findViewById(R.id.view_pager_home)
        bottom_view_home = fragView.findViewById(R.id.bottom_view_home)
        homeViewPager = HomeViewPager(mainActivity)
        return fragView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view_pager_home.adapter = homeViewPager
        view_pager_home.isUserInputEnabled = true

        bottom_view_home.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.btn_home_bottom -> view_pager_home.currentItem = 0
                R.id.btn_account_bottom -> view_pager_home.currentItem = 1
            }

            true
        }

        view_pager_home.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> bottom_view_home.menu.findItem(R.id.btn_home_bottom).isChecked = true
                    1 -> bottom_view_home.menu.findItem(R.id.btn_account_bottom).isChecked = true
                }
            }
        })
    }
}