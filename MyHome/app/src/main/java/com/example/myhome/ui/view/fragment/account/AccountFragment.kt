package com.example.myhome.ui.view.fragment.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.myhome.R
import com.example.myhome.data.model.login.UserProfileModel
import com.example.myhome.ui.view.activity.main.MainActivity
import com.example.myhome.ui.view.activity.typeUser.ManageUserActivity
import com.example.myhome.ui.view.dialog.DialogQuestion
import com.example.myhome.ui.viewmodel.typeuser.TypeUserViewModel
import com.example.myhome.utils.Constants
import de.hdodenhof.circleimageview.CircleImageView

class AccountFragment : Fragment() {

    private lateinit var mainActivity: MainActivity
    private lateinit var dialogQuestion: DialogQuestion
    private lateinit var lnAccount: LinearLayout
    private lateinit var btnSignOut: Button
    private lateinit var imgUser: CircleImageView
    private lateinit var username: TextView
    private lateinit var email: TextView
    private lateinit var btnManageUser: TextView
    private lateinit var typeUser: TextView
    private val typeUserViewModel: TypeUserViewModel by lazy {
        ViewModelProvider(this)[TypeUserViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_account, container, false)
        initView(view)

        /* default view value */
        showBtnManageUser(false)
        /* ****************** */

        // Inflate the layout for this fragment
        mainActivity = activity as MainActivity
        typeUserViewModel.getTypeUserFromFireStore(mainActivity)
        dialogQuestion = DialogQuestion(
            getString(R.string.title_sign_out),
            object : DialogQuestion.IClickDialogButton {
                override fun onClickCancel() {

                }

                override fun onClickOk() {
                    mainActivity.requestLogOut(
                        {
                            updateUser()
                            showBtnManageUser(false)
                        }, {

                        }
                    )
                }

            })
        typeUserViewModel.getTypeUser().observe(viewLifecycleOwner, {
            when (it) {
                Constants.MASTER_ID -> {
                    showBtnManageUser(true)
                    typeUser.text = Constants.MASTER
                }
                Constants.ROOT_ID -> {
                    showBtnManageUser(true)
                    typeUser.text = Constants.ROOT
                }
                Constants.NORMAL_ID -> {
                    showBtnManageUser(false)
                    typeUser.text = Constants.NORMAL
                }
                else -> {
                    showBtnManageUser(false)
                    typeUser.text = Constants.NONE
                }
            }
        })
        mainActivity.isLogIn().observe(viewLifecycleOwner, {
            updateUser()
            if (it) {
                if (typeUserViewModel.getTypeUser().value == 0 ||
                    typeUserViewModel.getTypeUser().value == 1
                ) {
                    showBtnManageUser(true)
                }
            } else {
                showBtnManageUser(false)
            }
        })
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateUser()
        lnAccount.setOnClickListener {
            if (!mainActivity.isSignIn()) {
                mainActivity.signIn()
            }
        }
        btnSignOut.setOnClickListener {
            if (!dialogQuestion.isAdded) {
                dialogQuestion.show(
                    mainActivity.supportFragmentManager,
                    getString(R.string.title_sign_out)
                )
            }
        }
        btnManageUser.setOnClickListener {
            /* open list user in firestore */
            val intent = Intent(mainActivity, ManageUserActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initView(view: View) {
        typeUser = view.findViewById(R.id.type_user)
        lnAccount = view.findViewById(R.id.ln_account)
        btnSignOut = view.findViewById(R.id.btn_sign_out)
        imgUser = view.findViewById(R.id.img_user)
        username = view.findViewById(R.id.username)
        email = view.findViewById(R.id.email)
        btnManageUser = view.findViewById(R.id.tv_btn_user_permission)
    }

    override fun onResume() {
        super.onResume()
        mainActivity.setTitleActionBar(getString(R.string.account))
        updateUser()
    }

    private fun updateUser() {
        if (mainActivity.isSignIn()) {
            btnSignOut.visibility = View.VISIBLE
            val userProfileModel: UserProfileModel? = mainActivity.getUserProfile()
            userProfileModel?.let {
                Glide.with(this)
                    .load(userProfileModel.photoUri)
                    .into(imgUser)
                username.text = it.displayName
                email.text = it.email
            }
            typeUser.text = mainActivity.getStringTypeUser()
        } else {
            btnSignOut.visibility = View.GONE
            Glide.with(this)
                .load(R.drawable.outline_account_circle_black_48dp)
                .into(imgUser)
            username.text = mainActivity.getString(R.string.username)
            email.text = mainActivity.getString(R.string.email)
            typeUser.text = mainActivity.getString(R.string.type_user)
        }
    }

    private fun showBtnManageUser(isShow: Boolean) {
        if (isShow) {
            btnManageUser.visibility = View.VISIBLE
        } else {
            btnManageUser.visibility = View.GONE
        }
    }
}