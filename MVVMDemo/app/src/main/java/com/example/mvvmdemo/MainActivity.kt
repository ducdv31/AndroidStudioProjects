package com.example.mvvmdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val myModel: UserViewModel by lazy {
        ViewModelProvider(this)[UserViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.lifecycleOwner = this
        binding.userModel = myModel
    }
}