package com.example.myroom.components.dialog

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.myroom.R
import com.example.myroom.activitymain.MainActivity
import com.example.myroom.activitymain.MyApplication
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.SignInButton
import de.hdodenhof.circleimageview.CircleImageView

class AccountDialog() : DialogFragment() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        var type: TextView? = null
    }

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val mainActivity: MainActivity = activity as MainActivity
            val builder = AlertDialog.Builder(it, R.style.MyDialogTheme)

            val inflate = requireActivity().layoutInflater
            val view = inflate.inflate(R.layout.dialog_account, null)

            builder.setView(view)
            val imgUser: CircleImageView = view.findViewById(R.id.img_user_login_dialog)
            val username: TextView = view.findViewById(R.id.username_dialog)
            val email: TextView = view.findViewById(R.id.email_dialog)
            type = view.findViewById(R.id.type_user_dialog)
            val btSignIn: SignInButton = view.findViewById(R.id.bt_sign_in_dialog)
            val btSignOut: Button = view.findViewById(R.id.bt_sign_out_dialog)

            updateUI(imgUser, username, email, btSignIn, btSignOut)
            MyApplication.getUIDPermission(mainActivity)

            val tfDialog = TFDialog(requireContext(), object : TFDialog.IDialogResponse {
                override fun onDialogResponse(response: Boolean) {
                    if (response) {
                        mainActivity.signOut()
                        updateUI(imgUser, username, email, btSignIn, btSignOut)
                    }
                }

            })

            btSignIn.setOnClickListener {
                mainActivity.signIn()
                dialog?.dismiss()
            }
            btSignOut.setOnClickListener {
                tfDialog.show(mainActivity.supportFragmentManager, "sign out")
            }

            builder.create()
        }!!
    }

    private fun updateUI(
        userImg: CircleImageView,
        Name: TextView,
        Email: TextView,
        sign_in: SignInButton,
        sign_out: Button
    ) {
        val acct = GoogleSignIn.getLastSignedInAccount(requireContext())
        if (acct != null) {
            val personName = acct.displayName
            val personEmail = acct.email
            val personId = acct.id
            val personPhoto = acct.photoUrl
            Name.text = personName
            Email.text = personEmail
            Glide.with(this).load(personPhoto).into(userImg)
            /* hide sign in button */
            sign_in.visibility = View.GONE
            sign_out.visibility = View.VISIBLE
        } else {
            Email.text = "username"
            Name.text = "example@email.com"
            userImg.setImageResource(R.drawable.outline_group_black_24dp)
            /* hide sign out button */
            sign_in.visibility = View.VISIBLE
            sign_out.visibility = View.GONE
        }

    }
}