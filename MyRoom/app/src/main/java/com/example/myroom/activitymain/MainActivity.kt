package com.example.myroom.activitymain

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.example.myroom.R
import com.example.myroom.activity2addmem.ActivityAddMem
import com.example.myroom.activitycalendar.ActivityCalendar
import com.example.myroom.activitylistmem.ActivityListMem
import com.example.myroom.activitymain.fragment.HomeFragment
import com.example.myroom.activitysummary.ActivitySummary
import com.example.myroom.activitytask.ActivityTask
import com.example.myroom.activityuserpermission.ActivityUserPermission
import com.example.myroom.components.`interface`.IPermissionRequest
import com.example.myroom.components.sharedpreference.DataLocalManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.*

class MainActivity : AppCompatActivity(), IPermissionRequest {

    /* interface */

    /* ************************** */
    private var backPressedTime: Long = 0
    private var mToast: Toast? = null
    lateinit var main_layout: DrawerLayout

    /* sign in */
    private lateinit var googleSignInClient: GoogleSignInClient
    var mAuth: FirebaseAuth? = null

    /* user detail */
    var personName: String? = null
    var personEmail: String? = null
    var personId: String? = null
    var personPhoto: Uri? = null

    /* show info */

    lateinit var Name: TextView
    lateinit var Email: TextView
    lateinit var id: TextView
    lateinit var accImg: ImageView

    /* ************** */
    companion object {
        const val PARENT_CHILD: String = "Deviot"
        const val PARENT_MONTH_CHILD: String = "Deviot-Month"
        const val NAME_CHILD: String = "name"
        const val RANK_CHILD: String = "rank"
        const val ROOM_CHILD: String = "room"
        const val USERNAME_CHILD: String = "username"
        const val EMAIL_CHILD: String = "email"
        const val URI_PHOTO_CHILD: String = "uri"
        const val PERM_CHILD: String = "perm"
        const val WORK_TIME_CHILD: String = "Work-Time"
        const val PARENT_DAY_CHILD: String = "Deviot-Date"
        const val PARENT_PERMISSION_CHILD: String = "Deviot-Permission"
        const val RC_SIGN_IN: Int = 12 // for sign in
    }

    private lateinit var appBarConfiguration: AppBarConfiguration
    private var drawerLayout: DrawerLayout? = null

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser: FirebaseUser? = mAuth?.currentUser
        if (currentUser == null) {
            Snackbar.make(main_layout, "Log in to see more", Snackbar.LENGTH_LONG)
                .setAction("Log in") {
                    signIn()
                }
                .setTextColor(Color.RED)
                .setActionTextColor(Color.GREEN)
                .show()
        }
        updateUI(currentUser)
    }

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        main_layout = findViewById(R.id.drawer_layout)
        /* sign in */
        mAuth = FirebaseAuth.getInstance()
        /* ******************** */

        /* navigation view */
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        val headerView: View = navigationView.getHeaderView(0)
        Name = headerView.findViewById(R.id.user_name_sign_in)
        Email = headerView.findViewById(R.id.mail_user_sign_in)
        accImg = headerView.findViewById(R.id.user_img)

        /* ******************* */

        drawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_about,
                R.id.nav_acc
            ),
            drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        createRequest()

        accImg.setOnClickListener {
            val acct = GoogleSignIn.getLastSignedInAccount(this)
            if (acct == null) {
                signIn()
            } else {
                val snackbar: Snackbar =
                    Snackbar.make(main_layout, "You are signed in", Snackbar.LENGTH_SHORT)
                snackbar.show()
            }
        }

        Name.setOnClickListener {
            val acct = GoogleSignIn.getLastSignedInAccount(this)
            if (acct == null) {
                signIn()
            } else {
                val snackbar: Snackbar =
                    Snackbar.make(main_layout, "You are signed in", Snackbar.LENGTH_SHORT)
                snackbar.show()
            }
        }

        Email.setOnClickListener {
            val acct = GoogleSignIn.getLastSignedInAccount(this)
            if (acct == null) {
                signIn()
            } else {
                val snackbar: Snackbar =
                    Snackbar.make(main_layout, "You are signed in", Snackbar.LENGTH_SHORT)
                snackbar.show()
            }
        }
        MyApplication.getUIDPermission(this)
    }

    override fun onResume() {
        super.onResume()
        MyApplication.getUIDPermission(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun openListMemActivity() {
        val intent = Intent(this, ActivityListMem::class.java)
        startActivity(intent)

    }

    fun openListDayActivity() {
        val intent = Intent(this, ActivityCalendar::class.java)
        startActivity(intent)

    }

    fun openSummaryActivity() {
        val intent = Intent(this, ActivitySummary::class.java)
        startActivity(intent)
    }

    private fun openAddMemActivity() {
        val intent = Intent(this, ActivityAddMem::class.java)
        startActivity(intent)
    }

    fun openTaskActivity() {
        val intent = Intent(this, ActivityTask::class.java)
        startActivity(intent)
    }

    fun openUserPermissionActivity() {
        val intent = Intent(this, ActivityUserPermission::class.java)
        startActivity(intent)
    }

    @SuppressLint("RtlHardcoded")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_mem -> openAddMemActivity()
            android.R.id.home -> {
                drawerLayout?.openDrawer(Gravity.LEFT)
            }
        }
        return true
    }

    override fun onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            mToast?.cancel()
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            startActivity(intent)
            finish()
            return
        } else {
            mToast = Toast.makeText(this, "Press Back again to Exit", Toast.LENGTH_SHORT)
            mToast?.show()
        }
        backPressedTime = System.currentTimeMillis()
    }

    /* sign in */
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
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!.idToken.toString())
                Log.e("Log in", "Success")
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                // ...
                updateUI(null)
                Log.e("Not ", "Log in")
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth?.signInWithCredential(credential)
            ?.addOnCompleteListener(
                this,
                OnCompleteListener<AuthResult?> { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user: FirebaseUser? = mAuth!!.currentUser
                        /* set User to database for determine permission */
                        user?.let {
                            MyApplication.setDataAfterLogIn(this, user)
                        }
                        MyApplication.getUIDPermission(this)
                        /* update UI */
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        DataLocalManager.setUID("")
                        updateUI(null)
                        Toast.makeText(
                            this@MainActivity,
                            "Error firebase Auth with google",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    // ...
                })
    }



    private fun updateUI(account: FirebaseUser?) {
        val acct = GoogleSignIn.getLastSignedInAccount(this)
        if (acct != null) {
            personName = acct.displayName
            personEmail = acct.email
            personId = acct.id
            personPhoto = acct.photoUrl
            Name.text = personName
            Email.text = personEmail
            Glide.with(this).load(personPhoto).into(accImg)
        } else {
            Email.text = "user account"
            Name.text = "user name"
            Glide.with(accImg).clear(accImg)
        }

    }

    fun signOut() {
        Email.text = "user account"
        Name.text = "user name"
//        id.setText("")
        Glide.with(accImg).clear(accImg)
        FirebaseAuth.getInstance().signOut()
        revokeAccess()
    }

    private fun revokeAccess() {
        googleSignInClient.revokeAccess()
            .addOnCompleteListener(this, OnCompleteListener<Void?> {
                // ...
            })
        MyApplication.getUIDPermission(this)
    }

    override fun hasUnKnowUser(has: Boolean) {
        if (has) {
            HomeFragment.bt_mode_list_user?.visibility = View.INVISIBLE
            HomeFragment.bt_mode_day_select?.visibility = View.INVISIBLE
            HomeFragment.bt_mode_summary?.visibility = View.INVISIBLE
            HomeFragment.bt_mode_permission_user?.visibility = View.INVISIBLE
        }
    }


    override fun hasUserInRoom(hasInRoom: Boolean, username: String) {
        if (hasInRoom) {
            HomeFragment.bt_mode_list_user?.visibility = View.VISIBLE
            HomeFragment.bt_mode_day_select?.visibility = View.VISIBLE
            HomeFragment.bt_mode_summary?.visibility = View.INVISIBLE
            HomeFragment.bt_mode_permission_user?.visibility = View.INVISIBLE
        }
    }

    override fun hasRootUser(hasRoot: Boolean) {
        if (hasRoot) {
            /* show */
            HomeFragment.bt_mode_list_user?.visibility = View.VISIBLE
            HomeFragment.bt_mode_summary?.visibility = View.VISIBLE
            HomeFragment.bt_mode_day_select?.visibility = View.VISIBLE
            HomeFragment.bt_mode_permission_user?.visibility = View.INVISIBLE
        }
    }

    override fun hasSupperRoot(hasSupperRoot: Boolean) {
        if (hasSupperRoot) {
            HomeFragment.bt_mode_list_user?.visibility = View.VISIBLE
            HomeFragment.bt_mode_summary?.visibility = View.VISIBLE
            HomeFragment.bt_mode_day_select?.visibility = View.VISIBLE
            HomeFragment.bt_mode_permission_user?.visibility = View.VISIBLE
        }
    }
}