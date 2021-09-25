package com.example.coroutine

import android.util.Log

var i = 0

@Synchronized
fun add() {
    Log.e("TAG", "add: ${i++}", )
}