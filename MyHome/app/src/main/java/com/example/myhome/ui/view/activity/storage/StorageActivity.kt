package com.example.myhome.ui.view.activity.storage

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.myhome.BaseActivity
import com.example.myhome.R
import com.google.android.material.bottomnavigation.BottomNavigationView


class StorageActivity : BaseActivity() {

    private val navHostFragment: NavHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.nav_host_storage) as NavHostFragment
    }

    private lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storage_main)
        setTitleActionBar(getString(R.string.storage))
        isShowUserImg(false)

        initVar()

        bottomNavigationView.setupWithNavController(navController)

    }

    private fun initVar() {
        navController = navHostFragment.navController
        bottomNavigationView = findViewById(R.id.bottom_nav_storage)
    }

}