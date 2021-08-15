package com.example.myhome.ui.view.fragment.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.myhome.R
import com.example.myhome.data.model.login.UserProfileModel
import com.example.myhome.ui.view.activity.main.MainActivity
import com.example.myhome.ui.view.dialog.DialogQuestion
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_account.*

class AccountFragment : Fragment() {

    private lateinit var mainActivity: MainActivity
    private lateinit var dialogQuestion: DialogQuestion

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        return inflater.inflate(R.layout.fragment_account, container, false)
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

    override fun onDestroyView() {
        super.onDestroyView()
        this.clearFindViewByIdCache()
    }
}