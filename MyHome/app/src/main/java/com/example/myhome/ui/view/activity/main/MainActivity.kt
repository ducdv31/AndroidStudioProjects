package com.example.myhome.ui.view.activity.main

import android.os.Bundle
import com.example.myhome.BaseActivity
import com.example.myhome.R
import com.example.myhome.ui.view.dialog.DialogOptionMain
import com.example.myhome.ui.view.fragment.main.HomeControllerFragment
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

    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }

}