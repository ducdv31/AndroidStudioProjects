package com.example.myhome.ui.view.activity.storage

import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.myhome.BaseActivity
import com.example.myhome.R
import com.example.myhome.ui.adapter.storage.StorageVewPagerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView


class StorageActivity : BaseActivity() {

    private lateinit var mViewPager: ViewPager2
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var mStorageViewPagerAdapter: StorageVewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storage_main)
        setTitleActionBar(getString(R.string.storage))
        isShowUserImg(false)

        initVar()

        initLogicViewPager()

    }

    private fun initVar() {
        mStorageViewPagerAdapter = StorageVewPagerAdapter(this)
        mViewPager = findViewById(R.id.vpg_storage)
        bottomNavigationView = findViewById(R.id.bottom_nav_storage)

    }

    private fun initLogicViewPager() {
        mViewPager.adapter = mStorageViewPagerAdapter
        mViewPager.offscreenPageLimit = 3

        mViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> bottomNavigationView.menu
                        .findItem(R.id.imageStorageFragment).isChecked = true
                    1 -> bottomNavigationView.menu
                        .findItem(R.id.videoStorageFragment).isChecked = true
                    2 -> bottomNavigationView.menu
                        .findItem(R.id.pdfStorageFragment).isChecked = true
                }
            }
        })

        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.imageStorageFragment -> mViewPager.currentItem = 0
                R.id.videoStorageFragment -> mViewPager.currentItem = 1
                R.id.pdfStorageFragment -> mViewPager.currentItem = 2
            }

            true
        }
    }

}