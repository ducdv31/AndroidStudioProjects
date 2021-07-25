package com.example.myhome.activitymain

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.myhome.R
import com.example.myhome.activitymain.dialog.DialogAccountMain
import com.example.myhome.activitymain.fragment.Dht11Fragment
import com.example.myhome.activitymain.model.UserProfileModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import java.io.Serializable

class MainActivity : AppCompatActivity() {
    private val DATA_MAIN = "Data from mainActivity"
    private val BACK_STACK = "Add to back stack main"
    private var actionBar: ActionBar? = null

    /* sign in */
    private lateinit var googleSignInClient: GoogleSignInClient
    var mAuth: FirebaseAuth? = null

    /* user detail */
    private var personName: String? = null
    private var personEmail: String? = null
    private var personId: String? = null
    private var personPhoto: Uri? = null

    /* result activity */
    private val getSignInResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!.idToken.toString())
                Log.e("Log in", "Success")
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                // ...
                updateUI()
                Log.e("Not ", "Log in")
            }
        }
    /* *************** */

    private var itemAcc: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        actionBar = supportActionBar
        actionBar?.setDisplayShowHomeEnabled(true)
        actionBar?.setDisplayUseLogoEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(false)
        actionBar?.setLogo(R.drawable.outline_home_white_36dp)

        createRequest()

        /* transfer to fragment */
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_main, Dht11Fragment())
        fragmentTransaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        itemAcc = menu?.findItem(R.id.account_menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.account_menu -> {
                val dialogAccountMain = DialogAccountMain()
                dialogAccountMain.show(supportFragmentManager, "Main dialog")
            }
            android.R.id.home -> onBackPressed()
        }
        return false
    }

    fun gotoActivity(cls: Class<*>) {
        val intent = Intent(this, cls)
        startActivity(intent)
    }

    private fun createRequest() {
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
                        this@MainActivity,
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

    private fun updateUI() {
        val acct = GoogleSignIn.getLastSignedInAccount(this)
        if (acct != null) {
            personName = acct.displayName
            personEmail = acct.email
            personId = acct.id
            personPhoto = acct.photoUrl

        } else {

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

    fun gotoFragment(fragment: Fragment, data: Any? = null, popBackStack: Boolean) {
        val fg = supportFragmentManager.beginTransaction()
        fg.replace(R.id.frame_main, fragment)
        data?.let {
            val bundle = Bundle()
            bundle.putSerializable(DATA_MAIN, data as Serializable)
            fragment.arguments = bundle
        }
        if (popBackStack) {
            fg.addToBackStack(BACK_STACK)
        }
        fg.commit()
    }

    fun setActionBar(title: String, showBack: Boolean) {
        actionBar?.title = title
        actionBar?.setDisplayHomeAsUpEnabled(showBack)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        supportFragmentManager.popBackStack()
    }
}