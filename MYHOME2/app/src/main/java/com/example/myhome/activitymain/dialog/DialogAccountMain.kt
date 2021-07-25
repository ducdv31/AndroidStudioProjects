package com.example.myhome.activitymain.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.myhome.R
import com.example.myhome.activitymain.MainActivity
import com.example.myhome.activitymain.fragment.AboutFragment
import com.example.myhome.activitymain.fragment.BoardInformationFragment
import de.hdodenhof.circleimageview.CircleImageView

class DialogAccountMain : DialogFragment() {
    private lateinit var userImg: CircleImageView
    private lateinit var username: TextView
    private lateinit var email: TextView
    private lateinit var layoutAcc: LinearLayout
    private lateinit var mainActivity: MainActivity
    private lateinit var signOut: TextView
    private lateinit var about: TextView
    private lateinit var boardInfor: TextView
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            val builder = AlertDialog.Builder(it, R.style.Trans20)

            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.dialog_account_main, null)

            builder.setView(view)

            initVar(view)
            setUp()

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun setUp() {
        val userProfileModel = mainActivity.getUserProfile()
        userProfileModel?.let {
            Glide.with(mainActivity).load(userProfileModel.photo).into(userImg)
            username.text = userProfileModel.name
            email.text = userProfileModel.email
        }
        if (!mainActivity.isSignIn()) {
            signOut.visibility = View.GONE
        } else {
            signOut.visibility = View.VISIBLE
        }

        layoutAcc.setOnClickListener {
            if (!mainActivity.isSignIn()) {
                mainActivity.signIn()
                dialog?.dismiss()
            } else {
                Toast.makeText(mainActivity, "You are signed in", Toast.LENGTH_SHORT).show()
            }

        }

        about.setOnClickListener {
            dialog?.dismiss()
            mainActivity.gotoFragment(AboutFragment(), null, true)
        }

        signOut.setOnClickListener {
            mainActivity.signOut()
            dialog?.dismiss()
        }

        boardInfor.setOnClickListener {
            dialog?.dismiss()
            mainActivity.gotoFragment(BoardInformationFragment(), null, true)
        }
    }

    private fun initVar(view: View) {
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