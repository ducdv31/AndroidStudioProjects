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
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import com.example.myroom.activityuserpermission.ActivityUserPermission
import com.example.myroom.components.`interface`.IPermissionRequest
import com.example.myroom.components.dialog.AccountDialog
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.*

class MainActivity : AppCompatActivity(), IPermissionRequest {

    /* dialog */
    private lateinit var accountDialog: AccountDialog
    /* ****** */
    // add 1
    // add 2
    // add 3
    /* interface */

    /* ************************** */
    private var backPressedTime: Long = 0
    private var mToast: Toast? = null
    private lateinit var mainLayout: DrawerLayout

    /* sign in */
    private lateinit var googleSignInClient: GoogleSignInClient
    var mAuth: FirebaseAuth? = null

    /* user detail */
    private var personName: String? = null
    private var personEmail: String? = null
    private var personId: String? = null
    private var personPhoto: Uri? = null

    /* show info */

    private lateinit var tvName: TextView
    private lateinit var tvEmail: TextView
    lateinit var id: TextView
    private lateinit var accImg: ImageView

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
    }

    private lateinit var appBarConfiguration: AppBarConfiguration
    private var drawerLayout: DrawerLayout? = null

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

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser: FirebaseUser? = mAuth?.currentUser
        if (currentUser == null) {
            Snackbar.make(mainLayout, "Log in to see more", Snackbar.LENGTH_LONG)
                .setAction("Log in") {
                    signIn()
                }
                .setTextColor(Color.RED)
                .setActionTextColor(Color.GREEN)
                .show()
        } else {
            MyApplication.setDataAfterLogIn(this, currentUser)
        }
        updateUI()
    }

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        mainLayout = findViewById(R.id.drawer_layout)
        /* sign in */
        mAuth = FirebaseAuth.getInstance()
        /* ******************** */
        /* dialog */
        accountDialog = AccountDialog()

        /* ****** */
        /* navigation view */
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        val headerView: View = navigationView.getHeaderView(0)
        tvName = headerView.findViewById(R.id.user_name_sign_in)
        tvEmail = headerView.findViewById(R.id.mail_user_sign_in)
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
                R.id.nav_about
            ),
            drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        createRequest()

        accImg.setOnClickListener {
            if (!accountDialog.isAdded) {
                accountDialog.show(supportFragmentManager, "DA")
                drawerLayout!!.closeDrawers()
            }
        }

        tvName.setOnClickListener {
            if (!accountDialog.isAdded) {
                accountDialog.show(supportFragmentManager, "DA")
                drawerLayout!!.closeDrawers()
            }
        }

        tvEmail.setOnClickListener {
            if (!accountDialog.isAdded) {
                accountDialog.show(supportFragmentManager, "DA")
                drawerLayout!!.closeDrawers()
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
                    user?.let {
                        MyApplication.setDataAfterLogIn(this, user)
                    }
                    MyApplication.getUIDPermission(this)
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



    private fun updateUI() {
        val acct = GoogleSignIn.getLastSignedInAccount(this)
        if (acct != null) {
            personName = acct.displayName
            personEmail = acct.email
            personId = acct.id
            personPhoto = acct.photoUrl
            tvName.text = personName
            tvEmail.text = personEmail
            Glide.with(this).load(personPhoto).into(accImg)
        } else {
            tvName.text = getString(R.string.nav_header_title)
            tvEmail.text = getString(R.string.nav_header_subtitle)
            Glide.with(accImg).clear(accImg)
            accImg.setImageResource(R.drawable.outline_group_black_24dp)
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
            }
        MyApplication.getUIDPermission(this)
    }

    override fun hasUnKnowUser(has: Boolean) {
        if (has) {
//            AccountFragment.typeUser?.text = "Not a member"
            AccountDialog.type?.text = getString(R.string.not_a_member)
            HomeFragment.bt_mode_list_user.visibility = View.INVISIBLE
            HomeFragment.bt_mode_day_select.visibility = View.INVISIBLE
            HomeFragment.bt_mode_summary.visibility = View.INVISIBLE
            HomeFragment.bt_mode_permission_user.visibility = View.INVISIBLE
        }
    }


    override fun hasUserInRoom(hasInRoom: Boolean, username: String) {
        if (hasInRoom) {
//            AccountFragment.typeUser?.text = "A member"
            AccountDialog.type?.text = getString(R.string.a_member)
            HomeFragment.bt_mode_list_user.visibility = View.VISIBLE
            HomeFragment.bt_mode_day_select.visibility = View.VISIBLE
            HomeFragment.bt_mode_summary.visibility = View.INVISIBLE
            HomeFragment.bt_mode_permission_user.visibility = View.INVISIBLE
        }
    }

    override fun hasRootUser(hasRoot: Boolean) {
        if (hasRoot) {
            /* show */
//            AccountFragment.typeUser?.text = "Root user"
            AccountDialog.type?.text = getString(R.string.root_user)
            HomeFragment.bt_mode_list_user.visibility = View.VISIBLE
            HomeFragment.bt_mode_summary.visibility = View.VISIBLE
            HomeFragment.bt_mode_day_select.visibility = View.VISIBLE
            HomeFragment.bt_mode_permission_user.visibility = View.INVISIBLE
        }
    }

    override fun hasSupperRoot(hasSupperRoot: Boolean) {
        if (hasSupperRoot) {
//            AccountFragment.typeUser?.text = "Super-root user"
            AccountDialog.type?.text = getString(R.string.super_root_user)
            HomeFragment.bt_mode_list_user.visibility = View.VISIBLE
            HomeFragment.bt_mode_summary.visibility = View.VISIBLE
            HomeFragment.bt_mode_day_select.visibility = View.VISIBLE
            HomeFragment.bt_mode_permission_user.visibility = View.VISIBLE
        }
    }
}