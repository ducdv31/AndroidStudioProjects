package com.example.coroutine.pack1

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.coroutine.R
import com.example.coroutine.SimpleThread
import kotlinx.coroutines.*
import kotlin.random.Random

class MainActivity2 : AppCompatActivity() {

    companion object {
        var a: Int = 10
    }

    private lateinit var btn: Button
    private val TAG = MainActivity2::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn = findViewById(R.id.btn)

        val job1 = CoroutineScope(Dispatchers.IO).async {
            delay(1000)
            "Hello"
        }

        val job2 = CoroutineScope(Dispatchers.IO).async {
            delay(2000)
            Random.nextInt()
        }

        CoroutineScope(Dispatchers.IO).launch {
            val a = job1.await() + job2.await()
            Log.e(TAG, "onCreate: $a")
        }

    }

}