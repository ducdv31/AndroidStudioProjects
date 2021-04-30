package com.example.myroom.activitymain

import android.app.Application
import com.example.myroom.sharedpreference.DataLocalManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DataLocalManager.init(applicationContext)
    }
}