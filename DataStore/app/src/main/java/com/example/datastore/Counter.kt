package com.example.datastore

import android.util.Log
import javax.inject.Inject
import kotlin.properties.Delegates

class Counter @Inject constructor() {

    private val TAG = Counter::class.java.simpleName

    var count by Delegates.notNull<Int>()

    fun upCount(){
        count++
        Log.e(TAG, "upCount: $count")
    }
}