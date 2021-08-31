package com.example.myhome.utils.sharedPreference

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.myhome.utils.Constants
import com.google.gson.Gson
import java.util.*

class MySharePreference(val context: Context) {

    private val TAG = MySharePreference::class.java.simpleName
    private val preferenceName = "My preference"

    fun putData(key: String, value: Any) {
        val sharePreference = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharePreference.edit()
        when (value) {
            is Int -> editor.putInt(key, value)
            is Long -> editor.putLong(key, value)
            is Boolean -> editor.putBoolean(key, value)
            is String -> editor.putString(key, value)
            is Float -> editor.putFloat(key, value)
            is Objects -> {
                val gson = Gson()
                val objectString: String = gson.toJson(value)
                editor.putString(key, objectString)
            }
            else -> Log.e(TAG, "Type data is not valid")
        }
        editor.apply()
    }

    fun <T> getData(key: String, cls: Class<T>): T {
        val sharedPreferences = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE)
        return when (cls) {
            String::class.java -> sharedPreferences.getString(key, Constants.EMPTY) as T
            Int::class.java -> sharedPreferences.getInt(key, 0) as T
            Float::class.java -> sharedPreferences.getFloat(key, 0f) as T
            Long::class.java -> sharedPreferences.getLong(key, 0) as T
            Boolean::class.java -> sharedPreferences.getBoolean(key, false) as T

            else -> {
                val dataStr = sharedPreferences.getString(key, Constants.EMPTY)
                Gson().fromJson(dataStr, cls)
            }
        }
    }

    fun removeData(key: String) {
        context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE)
            .edit().remove(key).apply()
    }

}