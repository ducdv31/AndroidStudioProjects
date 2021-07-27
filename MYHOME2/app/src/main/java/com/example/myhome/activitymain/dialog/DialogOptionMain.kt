package com.example.myhome.activitymain.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.myhome.R
import com.example.myhome.activitymain.MainActivity
import com.example.myhome.activitymain.fragment.AboutFragment
import com.example.myhome.activitymain.fragment.BoardInformationFragment
import de.hdodenhof.circleimageview.CircleImageView

class DialogOptionMain(context: Context, private val activity: Activity) : Dialog(context) {
    private lateinit var userImg: CircleImageView
    private lateinit var username: TextView
    private lateinit var email: TextView
    private lateinit var layoutAcc: LinearLayout
    private lateinit var mainActivity: MainActivity
    private lateinit var signOut: TextView
    private lateinit var about: TextView
    private lateinit var boardInfor: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.dialog_account_main, null)
        setContentView(view)
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        initVar(view, activity)
        setUp()
    }

    private fun setUp() {
        val userProfileModel = mainActivity.getUserProfile()
        userProfileModel?.let {
            Glide.with(mainActivity).load(userProfileModel.photo).into(userImg)
            username.text = userProfileModel.name
            email.text = userProfileModel.email
        }
        if (userProfileModel == null){
            Glide.with(mainActivity).clear(userImg)
            username.text = mainActivity.getString(R.string.user_name)
            email.text = mainActivity.getString(R.string.email)
        }
        if (!mainActivity.isSignIn()) {
            signOut.visibility = View.GONE
        } else {
            signOut.visibility = View.VISIBLE
        }

        layoutAcc.setOnClickListener {
            if (!mainActivity.isSignIn()) {
                mainActivity.signIn()
            } else {
                Toast.makeText(mainActivity, "You are signed in", Toast.LENGTH_SHORT).show()
            }

        }

        about.setOnClickListener {
            mainActivity.gotoFragment(
                AboutFragment(),
                null,
                true,
                activity.getString(R.string.about)
            )
            onBackPressed()
        }

        signOut.setOnClickListener {
            mainActivity.signOut()
            onBackPressed()
        }

        boardInfor.setOnClickListener {
            mainActivity.gotoFragment(
                BoardInformationFragment(),
                null,
                true,
                activity.getString(R.string.board_information)
            )
            onBackPressed()
        }
    }

    private fun initVar(view: View, activity: Activity) {
        mainActivity = activity as MainActivity
        userImg = view.findViewById(R.id.img_user)
        username = view.findViewById(R.id.username)
        email = view.findViewById(R.id.email)
        layoutAcc = view.findViewById(R.id.ln_account)
        signOut = view.findViewById(R.id.btn_sign_out)
        about = view.findViewById(R.id.btn_about)
        boardInfor = view.findViewById(R.id.board_information)
    }

}