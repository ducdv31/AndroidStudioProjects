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
import com.example.myhome.utils.animationViewPager2.ZoomOutPageTransformer
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeControllerFragment : Fragment() {
    private lateinit var mainActivity: MainActivity
    private lateinit var homeViewPager: HomeViewPager
    private lateinit var viewPagerHome: ViewPager2
    private lateinit var bottomViewHome: BottomNavigationView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragView = inflater.inflate(R.layout.fragment_home_controller, container, false)
        mainActivity = activity as MainActivity
        viewPagerHome = fragView.findViewById(R.id.view_pager_home)
        bottomViewHome = fragView.findViewById(R.id.bottom_view_home)
        homeViewPager = HomeViewPager(mainActivity)
        return fragView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPagerHome.apply {
            adapter = homeViewPager
            isUserInputEnabled = true
            setPageTransformer(ZoomOutPageTransformer())
            offscreenPageLimit = 2
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    when (position) {
                        0 -> bottomViewHome.menu.findItem(R.id.btn_home_bottom).isChecked = true
                        1 -> bottomViewHome.menu.findItem(R.id.btn_account_bottom).isChecked = true
                    }
                }
            })
        }

        bottomViewHome.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.btn_home_bottom -> viewPagerHome.currentItem = 0
                R.id.btn_account_bottom -> viewPagerHome.currentItem = 1
            }

            true
        }
    }
}