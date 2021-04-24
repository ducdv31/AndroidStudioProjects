package com.example.myroom.activitymain

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myroom.R
import com.example.myroom.activity2addmem.ActivityAddMem
import com.example.myroom.activitylistday.ActivityListDay
import com.example.myroom.activitylistmem.ActivityListMem
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private var backPressedTime: Long = 0
    private var mToast: Toast? = null

    companion object {
        const val PARENT_CHILD: String = "Deviot"
        const val NAME_CHILD: String = "name"
        const val RANK_CHILD: String = "rank"
        const val ROOM_CHILD: String = "room"
        const val WORK_TIME_CHILD: String = "Work-Time"
    }

    private lateinit var appBarConfiguration: AppBarConfiguration
    private var drawerLayout: DrawerLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home
            ),
            drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


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
        val intent = Intent(this, ActivityListDay::class.java)
        startActivity(intent)

    }

    private fun openAddMemActivity() {
        val intent = Intent(this, ActivityAddMem::class.java)
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
}