package com.example.myhome.ui.view.activity.main

import android.bluetooth.BluetoothAdapter
import android.content.IntentFilter
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.myhome.BaseActivity
import com.example.myhome.R
import com.example.myhome.ui.broadcast.MyBroadCastReceiver
import com.example.myhome.ui.view.dialog.DialogOptionMain

class MainActivity : BaseActivity() {

    private val TAG = MainActivity::class.java.simpleName
    private lateinit var dialogOptionMain: DialogOptionMain
    private lateinit var myBroadCastReceiver: MyBroadCastReceiver
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navHostController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myBroadCastReceiver = MyBroadCastReceiver()

        setTitleActionBar(getString(R.string.app_name))
        dialogOptionMain = DialogOptionMain(this)
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_main) as NavHostFragment
        navHostController = navHostFragment.navController

        isShowBackActionBar(false)
        updateUI()
    }

    override fun onStart() {
        super.onStart()
        val intentFilter = IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)
        registerReceiver(myBroadCastReceiver, intentFilter)
    }

    override fun onResume() {
        super.onResume()
        setTitleActionBar(getString(R.string.app_name))
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(myBroadCastReceiver)
    }

    override fun setOnClickUserImg() {
        super.setOnClickUserImg()
        if (!dialogOptionMain.isAdded) {
            dialogOptionMain.show(supportFragmentManager, TAG)
        }
    }

}