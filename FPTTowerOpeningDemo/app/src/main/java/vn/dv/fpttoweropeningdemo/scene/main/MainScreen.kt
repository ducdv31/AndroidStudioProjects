package vn.dv.fpttoweropeningdemo.scene.main

import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import vn.dv.fpttoweropeningdemo.base.BaseFragment
import vn.dv.fpttoweropeningdemo.databinding.FragmentMainScreenBinding
import vn.dv.fpttoweropeningdemo.scene.main.adapter.TabMainEnum
import vn.dv.fpttoweropeningdemo.scene.main.adapter.TabMainPagerAdapter

class MainScreen : BaseFragment<FragmentMainScreenBinding>(FragmentMainScreenBinding::inflate) {

    private val tabMainPagerAdapter: TabMainPagerAdapter by lazy {
        TabMainPagerAdapter(this)
    }

    override fun initData(data: Bundle?) {
    }

    override fun initViews() {
        with(binding) {
            viewPagerMain.apply {
                adapter = tabMainPagerAdapter
                isUserInputEnabled = false
            }
            setUpWithBottomNav(viewPagerMain)
        }
    }

    override fun initActions() {
    }

    override fun initListener() {
    }

    override fun initObservers() {
    }

    private fun setUpWithBottomNav(viewPager2: ViewPager2) {
        binding.bottomNavHome.setOnItemSelectedListener {
            when (it.itemId) {
                TabMainEnum.Home.idRes -> {
                    viewPager2.currentItem = TabMainEnum.Home.ordinal
                }
                TabMainEnum.SearchFlights.idRes -> {
                    viewPager2.currentItem = TabMainEnum.SearchFlights.ordinal
                }
                TabMainEnum.FlightStatus.idRes -> {
                    viewPager2.currentItem = TabMainEnum.FlightStatus.ordinal
                }
                TabMainEnum.MyTrips.idRes -> {
                    viewPager2.currentItem = TabMainEnum.MyTrips.ordinal
                }
                TabMainEnum.More.idRes -> {
                    viewPager2.currentItem = TabMainEnum.More.ordinal
                }
            }
            return@setOnItemSelectedListener true
        }
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    TabMainEnum.Home.ordinal -> {
                        binding.bottomNavHome.menu
                            .getItem(TabMainEnum.Home.ordinal).isChecked = true
                    }
                    TabMainEnum.SearchFlights.ordinal -> {
                        binding.bottomNavHome.menu
                            .getItem(TabMainEnum.SearchFlights.ordinal).isChecked = true
                    }
                    TabMainEnum.FlightStatus.ordinal -> {
                        binding.bottomNavHome.menu
                            .getItem(TabMainEnum.FlightStatus.ordinal).isChecked = true
                    }
                    TabMainEnum.MyTrips.ordinal -> {
                        binding.bottomNavHome.menu
                            .getItem(TabMainEnum.MyTrips.ordinal).isChecked = true
                    }
                    TabMainEnum.More.ordinal -> {
                        binding.bottomNavHome.menu
                            .getItem(TabMainEnum.More.ordinal).isChecked = true
                    }
                }
            }
        })
    }
}