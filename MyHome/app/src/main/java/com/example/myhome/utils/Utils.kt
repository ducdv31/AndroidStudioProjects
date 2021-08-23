package com.example.myhome.utils

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myhome.BaseActivity
import java.io.Serializable
import java.text.DateFormatSymbols
import java.time.LocalDate

class Utils {
    companion object {

        const val BACK_STACK_FRAGMENT = "pop fragment to back stack"
        const val DATA_BUNDLE = "data from bundle"

        @RequiresApi(Build.VERSION_CODES.O)
        fun getCurrentDate(): String {
            val localDate: LocalDate = LocalDate.now()
            val month =
                localDate.month.toString().substring(0, 1).uppercase() + localDate.month.toString()
                    .substring(1).lowercase()
            val day = if (localDate.dayOfMonth < 10) {
                "0${localDate.dayOfMonth}"
            } else {
                localDate.dayOfMonth
            }
            return "$day $month ${localDate.year}"
        }

        fun getMonth(num: Int): String {
            var month = ""
            val dfs = DateFormatSymbols()
            val months: Array<String> = dfs.months
            if (num in 0..11) {
                month = months[num]
            }
            return month
        }

        fun gotoFragment(
            activity: AppCompatActivity,
            res: Int,
            fragment: Fragment,
            isPopBackStack: Boolean,
            data: Any? = null
        ) {
            val ft = activity.supportFragmentManager.beginTransaction()
            ft.replace(res, fragment)
            if (isPopBackStack) {
                ft.addToBackStack(BACK_STACK_FRAGMENT)
            }
            data?.let {
                if (data is Serializable) {
                    val bundle = Bundle()
                    bundle.putSerializable(DATA_BUNDLE, data as Serializable?)
                    fragment.arguments = bundle
                }
            }
            ft.commit()
        }
    }
}