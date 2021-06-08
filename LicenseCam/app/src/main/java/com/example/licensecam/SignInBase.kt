//package com.example.licensecam
//
//import android.app.Application
//import android.content.Intent
//import android.net.Uri
//import android.util.Log
//import android.widget.Toast
//import com.google.android.gms.auth.api.signin.GoogleSignIn
//import com.google.android.gms.auth.api.signin.GoogleSignInClient
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions
//import com.google.android.gms.common.api.ApiException
//import com.google.firebase.auth.*
//import com.google.firebase.auth.R
//
//class SignInBase : Application(){
//
//    private lateinit var googleSignInClient: GoogleSignInClient
//    var mAuth: FirebaseAuth? = null
//
//    /* user detail */
//    private var personName: String? = null
//    private var personEmail: String? = null
//    private var personId: String? = null
//    private var personPhoto: Uri? = null
//
//    override fun onCreate() {
//        super.onCreate()
//        createRequest()
//    }
//    /* sign in */
//    private fun createRequest() {
//        // Configure Google Sign In
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.default_web_client_id))
//            .requestEmail()
//            .build()
//        // Build a GoogleSignInClient with the options specified by gso.
//        googleSignInClient = GoogleSignIn.getClient(this, gso)
//    }
//
//    fun signIn() {
//        val signInIntent: Intent = googleSignInClient.signInIntent
//        startActivityForResult(signInIntent, RC_SIGN_IN)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//            try {
//                // Google Sign In was successful, authenticate with Firebase
//                val account = task.getResult(ApiException::class.java)
//                firebaseAuthWithGoogle(account!!.idToken.toString())
//                Log.e("Log in", "Success")
//            } catch (e: ApiException) {
//                // Google Sign In failed, update UI appropriately
//                // ...
//                updateUI()
//                Log.e("Not ", "Log in")
//            }
//        }
//    }
//
//    private fun firebaseAuthWithGoogle(idToken: String) {
//        val credential = GoogleAuthProvider.getCredential(idToken, null)
//        mAuth?.signInWithCredential(credential)
//            ?.addOnCompleteListener(
//                this,
//                OnCompleteListener<AuthResult?> { task ->
//                    if (task.isSuccessful) {
//                        // Sign in success, update UI with the signed-in user's information
//                        val user: FirebaseUser? = mAuth!!.currentUser
//                        /* set User to database for determine permission */
//                        user?.let {
//                            MyApplication.setDataAfterLogIn(this, user)
//                        }
//                        MyApplication.getUIDPermission(this)
//                        /* update UI */
//                        updateUI()
//                    } else {
//                        // If sign in fails, display a message to the user.
//                        updateUI()
//                        Toast.makeText(
//                            this@MainActivity,
//                            "Error firebase Auth with google",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//
//                    // ...
//                })
//    }
//
//
//
//    private fun updateUI() {
//        val acct = GoogleSignIn.getLastSignedInAccount(this)
//        if (acct != null) {
//            personName = acct.displayName
//            personEmail = acct.email
//            personId = acct.id
//            personPhoto = acct.photoUrl
//            Name.text = personName
//            Email.text = personEmail
//            Glide.with(this).load(personPhoto).into(accImg)
//        } else {
//            Name.text = getString(R.string.nav_header_title)
//            Email.text = getString(R.string.nav_header_subtitle)
//            Glide.with(accImg).clear(accImg)
//            accImg.setImageResource(R.drawable.outline_group_black_24dp)
//        }
//
//    }
//
//    fun signOut() {
//        FirebaseAuth.getInstance().signOut()
//        revokeAccess()
//    }
//
//    private fun revokeAccess() {
//        googleSignInClient.revokeAccess()
//            .addOnCompleteListener(this, OnCompleteListener<Void?> {
//                // ...
//            })
//        updateUI()
//        MyApplication.getUIDPermission(this)
//    }
//}