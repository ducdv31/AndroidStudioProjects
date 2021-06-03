package com.example.coroutine

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    @ObsoleteCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CoroutineScope(Dispatchers.IO).launch {
            launch { // context of the parent, main runBlocking coroutine
                Log.e("main runBlocking", Thread.currentThread().name)
            }
            launch(Dispatchers.Unconfined) { // not confined -- will work with main thread
                Log.e("Unconfined", Thread.currentThread().name)
            }
            launch(Dispatchers.Default) { // will get dispatched to DefaultDispatcher
                Log.e("Default", Thread.currentThread().name)
            }
//            launch(Dispatchers.Main) {
//                Log.e("Main", Thread.currentThread().name)
//            }
            launch(newSingleThreadContext("MyOwnThread")) { // will get its own new thread
                Log.e("newSingleThreadContext", Thread.currentThread().name)
            }
        }

    }
}