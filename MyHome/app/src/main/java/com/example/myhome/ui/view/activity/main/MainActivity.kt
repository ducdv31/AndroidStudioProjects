package com.example.myhome.ui.view.activity.main

import android.bluetooth.BluetoothAdapter
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import com.example.myhome.BaseActivity
import com.example.myhome.R
import com.example.myhome.ui.view.dialog.DialogOptionMain
import com.example.myhome.ui.view.fragment.main.HomeControllerFragment
import com.example.myhome.utils.MyBroadCastReceiver
import com.google.android.gms.maps.SupportMapFragment

class MainActivity : BaseActivity() {

    private val TAG = MainActivity::class.java.simpleName
    private lateinit var dialogOptionMain: DialogOptionMain
    private lateinit var myBroadCastReceiver: MyBroadCastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myBroadCastReceiver = MyBroadCastReceiver()

        setTitleActionBar(getString(R.string.app_name))
        dialogOptionMain = DialogOptionMain(this)
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.frame_main, HomeControllerFragment())
        ft.commit()

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

    fun getSupportMapFragment(): SupportMapFragment {
        return supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
    }

    fun showToast(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

}