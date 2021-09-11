package com.example.myhome.data.repository

import android.content.Context
import com.example.myhome.utils.sharedPreference.MySharePreference

class MyDataLocal {

    private lateinit var mySharedPreferences: MySharePreference

    companion object {
        const val ID_CURRENT_USER = "ID Current User"
        private val myDataLocal: MyDataLocal = MyDataLocal()

        fun init(context: Context) {
            myDataLocal.mySharedPreferences = MySharePreference(context)
        }

        fun getInstance(): MyDataLocal {
            return myDataLocal
        }
    }

    fun putIDCurrentUser(id: String) {
        mySharedPreferences.putData(ID_CURRENT_USER, id)
    }

    fun getIDCurrentUser(): String {
        return mySharedPreferences.getData(ID_CURRENT_USER, String::class.java)
    }

    fun clearIDCurrentUser() {
        mySharedPreferences.removeData(ID_CURRENT_USER)
    }
}