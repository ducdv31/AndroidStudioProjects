package com.example.datastore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var btnSave: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSave = findViewById(R.id.btn_save)
        val userManager = UserManager(this)

        CoroutineScope(Dispatchers.IO).launch {
            launch {
                userManager.userNameFlow.collect {
                    Log.e("Username: ", it)
                }
            }
            launch {
                userManager.birthdayFlow.collect { birth ->
                    Log.e("Birthday: ", birth.toString())
                }
            }
        }

        btnSave.setOnClickListener {
            val userName = "Duc" + Random.nextInt()
            val birth = Random.nextInt()
            CoroutineScope(Dispatchers.IO).launch {
                userManager.saveDataStore(userName, birth)
            }
        }
    }
}