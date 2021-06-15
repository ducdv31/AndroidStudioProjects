package com.example.mvvm

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mvvm.model.UserViewModel

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        val model = UserViewModel()
        model.getNum().observe(this, { num ->
            // update UI
            Toast.makeText(this, "Up $num", Toast.LENGTH_SHORT).show()
        })
    }
}