package com.example.coroutine.pack1

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.coroutine.R
import com.example.coroutine.SimpleThread
import kotlinx.coroutines.*

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

        CoroutineScope(Dispatchers.IO).launch {
            testCoroutine()
            repeat(100) {
                launch {
                    delay(1000)
                    testCoroutine()
                    Log.e(TAG, "onCreate: ${Thread.currentThread().id}")
                }
            }
        }

    }

    private fun testCoroutine() {
        Log.e(TAG, "testCoroutine: Ok")
    }

    private fun block() = equalFun {

    }

    private fun equalFun(onSuccess: () -> Unit) {
        onSuccess.invoke()
    }
}