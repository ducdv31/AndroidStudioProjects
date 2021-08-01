package com.example.mvvmbeginner.ui.main.view.activity.mainactivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmbeginner.R
import com.example.mvvmbeginner.databinding.ActivityMainBinding
import com.example.mvvmbeginner.ui.main.viewmodel.ThViewmodel

class MainActivity : AppCompatActivity() {

    private val viewModel: ThViewmodel by lazy {
        ViewModelProvider(this)[ThViewmodel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.lifecycleOwner = this
        binding.thViewModel = viewModel
    }
}