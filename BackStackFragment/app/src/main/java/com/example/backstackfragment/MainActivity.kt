package com.example.backstackfragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val value = supportFragmentManager.beginTransaction();
        value.replace(R.id.content_frame, UsersFragment())
        value.commit()
    }

    fun gotoDetailFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val detailFragment = DetailFragment()
        val bundle = Bundle()
        bundle.putString("Name", "Duc")

    }
}