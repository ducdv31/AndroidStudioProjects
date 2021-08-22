package com.example.myhome

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.TextView
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

open class BaseActivity : AppCompatActivity() {

    private val TAG = BaseActivity::class.java.simpleName
    private lateinit var googleSignInClient: GoogleSignInClient
    private var mAuth: FirebaseAuth? = null
    private lateinit var btn_back_actionBar: CircleImageView
    private lateinit var img_user: CircleImageView
    private lateinit var title_action_bar: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLogIn()
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        btn_back_actionBar = findViewById(R.id.btn_back_action_bar)
        img_user = findViewById(R.id.img_user)
        title_action_bar = findViewById(R.id.title_action_bar)
        startListenImgUserClick()
        startListenBackActionBar()
    }

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
        btn_back_actionBar.setOnClickListener {
            setOnBackActionBar()
        }
    }

    fun startListenImgUserClick() {
        img_user.setOnClickListener {
            setOnClickUserImg()
        }
    }

    open fun setBackRes(res: Int) {
        btn_back_actionBar.setImageResource(res)
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
            btn_back_actionBar.visibility = View.VISIBLE
        } else {
            btn_back_actionBar.visibility = View.GONE
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

    private fun createRequest() {
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id_2))
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
                    acct.photoUrl?.toString(),
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
                .load(acct.photoUrl?.toString())
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


    /* set user profile to fireStore */
    fun setUserToFireStore() {
        val userProfileModel = getUserProfile()
        userProfileModel?.let {
            userProfileModel.id?.let { it1 ->
                Firebase.firestore.collection(Constants.PERMISSION).document(it1)
                    .set(it, SetOptions.merge())
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
                    .addOnSuccessListener { }
                    .addOnFailureListener { }
            }
        }
    }

    /* ***************************** */
}