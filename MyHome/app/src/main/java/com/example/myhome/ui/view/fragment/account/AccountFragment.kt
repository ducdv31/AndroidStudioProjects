package com.example.myhome.ui.view.fragment.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.myhome.R
import com.example.myhome.data.model.login.UserProfileModel
import com.example.myhome.ui.view.activity.main.MainActivity
import com.example.myhome.ui.view.dialog.DialogQuestion
import de.hdodenhof.circleimageview.CircleImageView

class AccountFragment : Fragment() {

    private lateinit var mainActivity: MainActivity
    private lateinit var dialogQuestion: DialogQuestion
    private lateinit var ln_account: LinearLayout
    private lateinit var btn_sign_out: Button
    private lateinit var img_user: CircleImageView
    private lateinit var username: TextView
    private lateinit var email: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_account, container, false)
        ln_account = view.findViewById(R.id.ln_account)
        btn_sign_out = view.findViewById(R.id.btn_sign_out)
        img_user = view.findViewById(R.id.img_user)
        username = view.findViewById(R.id.username)
        email = view.findViewById(R.id.email)
        // Inflate the layout for this fragment
        mainActivity = activity as MainActivity
        dialogQuestion = DialogQuestion(
            getString(R.string.title_sign_out),
            object : DialogQuestion.IClickDialogButton {
                override fun onClickCancel() {

                }

                override fun onClickOk() {
                    mainActivity.signOut()
                    updateUser()
                }

            })
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateUser()
        ln_account.setOnClickListener {
            if (!mainActivity.isSignIn()) {
                mainActivity.signIn()
            }
        }
        btn_sign_out.setOnClickListener {
            if (!dialogQuestion.isAdded) {
                dialogQuestion.show(
                    mainActivity.supportFragmentManager,
                    getString(R.string.title_sign_out)
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mainActivity.setTitleActionBar(getString(R.string.account))
        updateUser()
    }

    private fun updateUser() {
        if (mainActivity.isSignIn()) {
            btn_sign_out.visibility = View.VISIBLE
            val userProfileModel: UserProfileModel? = mainActivity.getUserProfile()
            userProfileModel?.let {
                Glide.with(this)
                    .load(userProfileModel.photoUri)
                    .into(img_user)
                username.text = it.displayName
                email.text = it.email
            }
        } else {
            btn_sign_out.visibility = View.GONE
            Glide.with(this)
                .load(R.drawable.outline_account_circle_black_48dp)
                .into(img_user)
            username.text = mainActivity.getString(R.string.username)
            email.text = mainActivity.getString(R.string.email)
        }
    }
}