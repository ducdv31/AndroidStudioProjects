package com.example.coroutine

import android.content.ContentValues.TAG
import android.util.Log

class SimpleThread(private val name2: String) : Thread() {
    override fun run() {
        repeat (200) {
            Log.e(TAG, "run: $name2")
            add()
        }
    }

}