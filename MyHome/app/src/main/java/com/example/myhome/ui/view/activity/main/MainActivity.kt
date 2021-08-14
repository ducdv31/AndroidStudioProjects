package com.example.myhome.ui.view.activity.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.myhome.BaseActivity
import com.example.myhome.R
import com.example.myhome.ui.view.fragment.main.HomeMainFragment
import com.example.myhome.ui.viewmodel.dht.DhtViewmodel

class MainActivity : BaseActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.frame_main, HomeMainFragment())
        ft.commit()

        isShowBackActionBar(false)
    }

}