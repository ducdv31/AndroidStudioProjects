package com.example.myroom.components.sharedpreference

import android.content.Context

class MySharedPreference(context: Context) {
    private val MY_SHARED_PREFERENCE = "MY_SHARED_PREFERENCE"
    private var context: Context? = null
    init {
        this.context = context
    }

    /* Boolean */
    fun putBooleanValue(key: String?, value: Boolean) {
        val sharedPreferences = context!!.getSharedPreferences(
            MY_SHARED_PREFERENCE,
            Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getBooleanValue(key: String?): Boolean {
        val sharedPreferences = context!!.getSharedPreferences(
            MY_SHARED_PREFERENCE,
            Context.MODE_PRIVATE
        )
        return sharedPreferences.getBoolean(key, false)
    }

    /* Int */
    fun putIntValue(key: String?, value: Int) {
        val sharedPreferences = context!!.getSharedPreferences(
            MY_SHARED_PREFERENCE,
            Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun getIntValue(key: String?): Int {
        val sharedPreferences = context!!.getSharedPreferences(
            MY_SHARED_PREFERENCE,
            Context.MODE_PRIVATE
        )
        return sharedPreferences.getInt(key, 0)
    }

    /* Float */
    fun putFloatValue(key: String?, value: Float) {
        val sharedPreferences = context!!.getSharedPreferences(
            MY_SHARED_PREFERENCE,
            Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor.putFloat(key, value)
        editor.apply()
    }

    fun getFloatValue(key: String?): Float {
        val sharedPreferences = context!!.getSharedPreferences(
            MY_SHARED_PREFERENCE,
            Context.MODE_PRIVATE
        )
        return sharedPreferences.getFloat(key, 0f)
    }

    /* Long */
    fun putDoubleValue(key: String?, value: Long) {
        val sharedPreferences = context!!.getSharedPreferences(
            MY_SHARED_PREFERENCE,
            Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor.putLong(key, value)
        editor.apply()
    }

    fun getDoubleValue(key: String?): Long {
        val sharedPreferences = context!!.getSharedPreferences(
            MY_SHARED_PREFERENCE,
            Context.MODE_PRIVATE
        )
        return sharedPreferences.getLong(key, 0)
    }

    /* String */
    fun putStringValue(key: String?, value: String?) {
        val sharedPreferences = context!!.getSharedPreferences(
            MY_SHARED_PREFERENCE,
            Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getStringValue(key: String?): String? {
        val sharedPreferences = context!!.getSharedPreferences(
            MY_SHARED_PREFERENCE,
            Context.MODE_PRIVATE
        )
        return sharedPreferences.getString(key, "")
    }
}