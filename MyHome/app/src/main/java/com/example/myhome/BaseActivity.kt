package com.example.myhome

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.myhome.data.model.login.UserProfileModel
import com.example.myhome.utils.Constants
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.my_action_bar.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.HashMap

open class BaseActivity : AppCompatActivity() {

    private val TAG = BaseActivity::class.java.simpleName
    private lateinit var googleSignInClient: GoogleSignInClient
    private var mAuth: FirebaseAuth? = null

    /* result activity */
    private val getSignInResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!.idToken!!.toString())
                setUserToFireStore()
                showToast("Sign In Successfully")
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                // ...
                updateUI()
                showToast("Sign In Fail")
            }
        }

    private fun showToast(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    /* *************** */
    fun startListenBackActionBar() {
        btn_back_action_bar.setOnClickListener {
            setOnBackActionBar()
        }
    }

    fun startListenImgUserClick() {
        img_user.setOnClickListener {
            setOnClickUserImg()
        }
    }

    open fun setBackRes(res: Int) {
        btn_back_action_bar.setImageResource(res)
    }

    open fun setUserImgRes(res: Int) {
        img_user.setImageResource(res)
    }

    open fun setOnBackActionBar() {
        onBackPressed()
    }

    open fun setOnClickUserImg() {

    }

    open fun setTitleActionBar(title: String) {
        title_action_bar.text = title
    }

    open fun isShowBackActionBar(isShow: Boolean) {
        if (isShow) {
            btn_back_action_bar.visibility = View.VISIBLE
        } else {
            btn_back_action_bar.visibility = View.GONE
        }
    }

    open fun setShowUserImg(isShow: Boolean) {
        if (isShow) {
            img_user.visibility = View.VISIBLE
        } else {
            img_user.visibility = View.GONE
        }
    }

    fun getUserImgView(): CircleImageView {
        return img_user
    }

    /* sign in */
    fun initLogIn() {
        createRequest()
    }

    fun createRequest() {
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        // Build a GoogleSignInClient with the options specified by gso.
        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    fun signIn() {
        val signInIntent: Intent = googleSignInClient.signInIntent
        getSignInResult.launch(signInIntent)
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth = FirebaseAuth.getInstance()
        mAuth?.signInWithCredential(credential)
            ?.addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user: FirebaseUser? = mAuth!!.currentUser
                    /* set User to database for determine permission */
                    /* update UI */
                    updateUI()
                } else {
                    // If sign in fails, display a message to the user.
                    updateUI()
                    Toast.makeText(
                        this,
                        "Error firebase Auth with google",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                // ...
            }
    }

    fun isSignIn(): Boolean {
        val acct = GoogleSignIn.getLastSignedInAccount(this)
        return acct != null
    }

    fun getUserProfile(): UserProfileModel? {
        if (isSignIn()) {
            val acct = GoogleSignIn.getLastSignedInAccount(this)
            acct?.let {
                return UserProfileModel(
                    acct.id,
                    acct.photoUrl,
                    acct.displayName,
                    acct.email
                )
            } ?: return null

        } else {
            return null
        }
    }

    fun updateUI() {
        setUserToFireStore()
        val acct = GoogleSignIn.getLastSignedInAccount(this)
        if (acct != null) {
            Glide.with(this)
                .load(acct.photoUrl)
                .into(getUserImgView())
        } else {
            Glide.with(this).load(R.drawable.outline_account_circle_black_48dp)
                .into(getUserImgView())
        }

    }

    fun signOut() {
        FirebaseAuth.getInstance().signOut()
        revokeAccess()
    }

    private fun revokeAccess() {
        googleSignInClient.revokeAccess()
            .addOnCompleteListener(this) {
                // ...
                updateUI()
                Toast.makeText(this, "Sign out successfully", Toast.LENGTH_SHORT).show()
            }
    }

    /* end sign in */
    val user = hashMapOf(
        "first" to "Alan",
        "middle" to "Mathison",
        "last" to "Turing",
        "born" to 1912
    )

    /* set user profile to fireStore */
    fun setUserToFireStore() {
        val userProfileModel = getUserProfile()
        userProfileModel?.let {
            userProfileModel.id?.let { it1 ->
                Firebase.firestore.collection(Constants.PERMISSION).document(it1)
                    .set(convertToCollection(it), SetOptions.merge())
                    .addOnSuccessListener {

                    }
                    .addOnFailureListener {

                    }
            }
        }
    }

    fun setUserPermission(perm: Int) {
        val userProfileModel = getUserProfile()
        userProfileModel?.let {
            it.id?.let { it1 ->
                Firebase.firestore.collection(Constants.PERMISSION).document(it1)
                    .set(hashMapOf(Constants.PERMISSION to perm), SetOptions.merge())
            }
        }
    }

    fun convertToCollection(userProfileModel: UserProfileModel?): HashMap<Any, Any?> {
        return hashMapOf(
            Constants.ID to userProfileModel?.id,
            Constants.USERNAME to userProfileModel?.displayName,
            Constants.EMAIL to userProfileModel?.email,
            Constants.URIPHOTO to userProfileModel?.photoUri.toString()
        )
    }

    /* ***************************** */

    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }
}