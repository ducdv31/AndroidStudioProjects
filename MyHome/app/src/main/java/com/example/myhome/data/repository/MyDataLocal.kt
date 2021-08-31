package com.example.myhome.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.example.myhome.utils.sharedPreference.MySharePreference

class MyDataLocal {

    private lateinit var mySharedPreferences: MySharePreference

    companion object {
        private var muDataLocal: MyDataLocal = MyDataLocal()

        fun init(context: Context) {
            muDataLocal.mySharedPreferences = MySharePreference(context)
        }
    }
}