package com.example.myhome

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.myhome.data.model.login.UserProfileModel
import com.example.myhome.data.repository.UserLoginLocalStore
import com.example.myhome.ui.viewmodel.typeuser.TypeUserViewModel
import com.example.myhome.utils.Constants
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class BaseActivity : AppCompatActivity() {

    companion object {
        var idLd: MutableLiveData<String> = MutableLiveData(Constants.EMPTY)
        var isLogIn: MutableLiveData<Boolean> = MutableLiveData(false)
    }

    private val TAG = BaseActivity::class.java.simpleName
    private lateinit var googleSignInClient: GoogleSignInClient
    private var mAuth: FirebaseAuth? = null
    private lateinit var btnBackActionBar: CircleImageView
    private lateinit var imgUser: CircleImageView
    private lateinit var titleActionBar: TextView
    val typeUserViewModel: TypeUserViewModel by lazy {
        ViewModelProvider(this)[TypeUserViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createRequest()
        typeUserViewModel.getTypeUserFromFireStore(this)
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        btnBackActionBar = findViewById(R.id.btn_back_action_bar)
        imgUser = findViewById(R.id.img_user)
        titleActionBar = findViewById(R.id.title_action_bar)
        startListenImgUserClick()
        startListenBackActionBar()
    }

    /* result activity */
    private val getSignInResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            val task: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account: GoogleSignInAccount = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken!!.toString())
                setUserToFireStore()
                showToast("Sign In Successfully")
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                // ...
                isLogIn.value = false
                updateUI()
                showToast("Sign In Fail")
            }
        }

    fun showToast(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    /* *************** */

    private fun startListenBackActionBar() {
        btnBackActionBar.setOnClickListener {
            setOnBackActionBar()
        }
    }

    private fun startListenImgUserClick() {
        imgUser.setOnClickListener {
            setOnClickUserImg()
        }
    }

    open fun setBackRes(res: Int) {
        btnBackActionBar.setImageResource(res)
    }

    open fun setUserImgRes(res: Int) {
        imgUser.setImageResource(res)
    }

    open fun setOnBackActionBar() {
        onBackPressed()
    }

    open fun setOnClickUserImg() {

    }

    open fun setTitleActionBar(title: String) {
        titleActionBar.text = title
    }

    open fun isShowBackActionBar(isShow: Boolean) {
        if (isShow) {
            btnBackActionBar.visibility = View.VISIBLE
        } else {
            btnBackActionBar.visibility = View.GONE
        }
    }

    open fun isShowUserImg(isShow: Boolean) {
        if (isShow) {
            imgUser.visibility = View.VISIBLE
        } else {
            imgUser.visibility = View.GONE
        }
    }

    fun getUserImgView(): CircleImageView {
        return imgUser
    }

    /* sign in */

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
                    isLogIn.value = true
                    // Sign in success, update UI with the signed-in user's information
                    val user: FirebaseUser? = mAuth!!.currentUser
                    /* set User to database for determine permission */
                    /* update UI */
                    updateUI()
                } else {
                    isLogIn.value = false
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

    fun isLogIn(): MutableLiveData<Boolean> {
        return isLogIn
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
        typeUserViewModel.getTypeUserFromFireStore(this)
        val acct = GoogleSignIn.getLastSignedInAccount(this)
        if (acct != null) {
            idLd.value = acct.id
            Glide.with(this)
                .load(acct.photoUrl?.toString())
                .into(getUserImgView())
            acct.id?.let {
                CoroutineScope(Dispatchers.IO).launch {
                    UserLoginLocalStore.getInstant(context = this@BaseActivity).setIdUserLogin(it)
                }
            }
            typeUserViewModel.getTypeUser()
        } else {
            idLd.value = Constants.EMPTY
            Glide.with(this).load(R.drawable.outline_account_circle_black_48dp)
                .into(getUserImgView())
            CoroutineScope(Dispatchers.IO).launch {
                UserLoginLocalStore.getInstant(this@BaseActivity).setIdUserLogin(Constants.EMPTY)
            }
        }

    }

    fun requestLogOut(
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        FirebaseAuth.getInstance().signOut()
        googleSignInClient.revokeAccess()
            .addOnCompleteListener(this) {
                // ...
                onSuccess.invoke()
                isLogIn.value = false
                typeUserViewModel.clearTypeUser()
                updateUI()
                Toast.makeText(this, "Sign out successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                onFailure.invoke()
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

    fun setUserPermission(id: String, perm: Int) {
        Firebase.firestore.collection(Constants.PERMISSION).document(id)
            .set(hashMapOf(Constants.PERMISSION to perm), SetOptions.merge())
            .addOnSuccessListener {
                showToast(getString(R.string.set_user_ok))
            }
            .addOnFailureListener {
                showToast(getString(R.string.set_user_fail))
            }
    }

    fun getCurrentUserPermission(): Int {
        val acc = GoogleSignIn.getLastSignedInAccount(this)
        var type: Int = 99
        if (acc != null) {
            FirebaseFirestore.getInstance().collection(Constants.PERMISSION)
                .document(acc.id!!).addSnapshotListener { value, error ->
                    type = value?.get(Constants.PERMISSION).toString().toInt()
                    Log.e(TAG, "Type: $type")
                }
        }
        Log.e(TAG, "Out type: $type")
        return type
    }

    /* ***************************** */

    fun getSupportMapFragment(): SupportMapFragment {
        return supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
    }


    fun clearData(sensorName: String, day: String, time: String) {
        FirebaseDatabase.getInstance().reference
            .child(sensorName).child(Constants.HISTORY_CHILD)
            .child(day).child(time).setValue(null)
    }

    fun getStringTypeUser(): String {
        return when (typeUserViewModel.getTypeUser().value) {
            Constants.MASTER_ID -> Constants.MASTER
            Constants.ROOT_ID -> Constants.ROOT
            Constants.NORMAL_ID -> Constants.NORMAL
            else -> Constants.NONE
        }
    }

    fun checkShowHideByUser(
        onShow: () -> Unit,
        onHide: () -> Unit
    ) {
        typeUserViewModel.getTypeUser().observe(this, {
            when (it) {
                Constants.MASTER_ID -> {
                    onShow.invoke()
                }
                Constants.ROOT_ID -> {
                    onShow.invoke()
                }
                Constants.NORMAL_ID -> {
                    onHide.invoke()
                }
                else -> {
                    onHide.invoke()
                }
            }
        })
    }
}