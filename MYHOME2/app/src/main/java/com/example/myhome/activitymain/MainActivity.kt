package com.example.myhome.activitymain

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.myhome.R
import com.example.myhome.activitymain.fragment.Dht11Fragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayShowHomeEnabled(true)
        actionBar?.setDisplayUseLogoEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(false)
        actionBar?.setLogo(R.drawable.outline_home_white_36dp)

        /* transfer to fragment */
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_main, Dht11Fragment())
        fragmentTransaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.account_menu -> {
                Toast.makeText(this, "Acc", Toast.LENGTH_SHORT).show()
            }
        }
        return false
    }

    fun gotoActivity(cls: Class<*>) {
        val intent = Intent(this, cls)
        startActivity(intent)
    }
}