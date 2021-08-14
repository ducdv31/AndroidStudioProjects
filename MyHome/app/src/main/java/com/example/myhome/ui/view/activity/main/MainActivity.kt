package com.example.myhome.ui.view.activity.main

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.myhome.BaseActivity
import com.example.myhome.R
import com.example.myhome.data.model.login.UserProfileModel
import com.example.myhome.ui.view.fragment.main.HomeMainFragment
import com.example.myhome.ui.viewmodel.dht.DhtViewmodel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.my_action_bar.*

class MainActivity : BaseActivity() {

    private val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initLogIn()
        startListenBackActionBar()
        startListenImgUserClick()
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.frame_main, HomeMainFragment())
        ft.commit()

        isShowBackActionBar(false)

    }

    override fun setOnClickUserImg() {
        super.setOnClickUserImg()
        if (!isSignIn()) {
            signIn()
        } else {
            signOut()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }

}