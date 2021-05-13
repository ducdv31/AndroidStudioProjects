package com.example.myroom.activitymain.fragment

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.myroom.R
import com.example.myroom.activitymain.MainActivity
import com.example.myroom.activitymain.MyApplication
import com.example.myroom.components.dialog.TFDialog
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.SignInButton
import com.google.firebase.auth.FirebaseUser

class AccountFragment : Fragment() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        var typeUser: TextView? = null
    }

    lateinit var mainActivity: MainActivity
    lateinit var userImg: ImageView
    lateinit var Name: TextView
    lateinit var Email: TextView
    lateinit var sign_in: SignInButton
    lateinit var sign_out: Button

    var personName: String? = null
    var personEmail: String? = null
    var personId: String? = null
    var personPhoto: Uri? = null

    /* dialog */
    lateinit var TFDialog: TFDialog

    /* **************** */

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val accountView = inflater.inflate(R.layout.fragment_account, container, false)
        mainActivity = activity as MainActivity
        Name = accountView.findViewById(R.id.user_name_account_fragment)
        Email = accountView.findViewById(R.id.email_user__account_fragment)
        userImg = accountView.findViewById(R.id.img_user_account_fragment)
        sign_in = accountView.findViewById(R.id.sign_in_account_fragment)
        sign_out = accountView.findViewById(R.id.sign_out_account_fragment)
        typeUser = accountView.findViewById(R.id.type_account_fragment)
        TFDialog = TFDialog(requireContext(), object : TFDialog.IDialogResponse {
            override fun onDialogResponse(response: Boolean) {
                if (response) {
                    mainActivity.signOut()
                    checkAcc()
                }
            }

        })

        sign_in.setOnClickListener { mainActivity.signIn() }
        sign_out.setOnClickListener {
            TFDialog.show(requireActivity().supportFragmentManager, "Sign out")
        }

        checkAcc()
        MyApplication.getUIDPermission(mainActivity)
        return accountView
    }

    override fun onResume() {
        super.onResume()
        checkAcc()
        MyApplication.getUIDPermission(mainActivity)
    }

    private fun checkAcc() {
        val currentUser: FirebaseUser? = mainActivity.mAuth?.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(account: FirebaseUser?) {
        val acct = GoogleSignIn.getLastSignedInAccount(requireContext())
        if (acct != null) {
            personName = acct.displayName
            personEmail = acct.email
            personId = acct.id
            personPhoto = acct.photoUrl
            Name.text = personName
            Email.text = personEmail
//        id.setText(personId)
            Glide.with(this).load(personPhoto).into(userImg)
            /* hide sign in button */
            sign_in.visibility = View.GONE
            sign_out.visibility = View.VISIBLE
        } else {
            Email.text = "user account"
            Name.text = "user name"
            Glide.with(userImg).clear(userImg)
            /* hide sign out button */
            sign_in.visibility = View.VISIBLE
            sign_out.visibility = View.GONE
        }

    }
}