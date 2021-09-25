package com.example.coroutine.pack1

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.coroutine.R
import com.example.coroutine.SimpleThread

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

        val simpleThread1 = SimpleThread("Thread 1")
        val simpleThread2 = SimpleThread("Thread 2")
        simpleThread1.start()
        simpleThread2.start()

    }
}