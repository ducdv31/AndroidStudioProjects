package com.example.myhome.ui.view.activity.main

import android.os.Bundle
import android.widget.Toast
import com.example.myhome.BaseActivity
import com.example.myhome.R
import com.example.myhome.ui.view.dialog.DialogOptionMain
import com.example.myhome.ui.view.fragment.main.HomeControllerFragment
import com.google.android.gms.maps.SupportMapFragment
import kotlinx.android.synthetic.*

class MainActivity : BaseActivity() {

    private val TAG = MainActivity::class.java.simpleName
    private lateinit var dialogOptionMain: DialogOptionMain

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initLogIn()
        startListenBackActionBar()
        startListenImgUserClick()
        dialogOptionMain = DialogOptionMain()
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.frame_main, HomeControllerFragment())
        ft.commit()

        isShowBackActionBar(false)
        updateUI()
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

    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }

}