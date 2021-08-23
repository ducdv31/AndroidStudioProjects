package com.example.myhome.ui.view.dialog

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.myhome.R
import com.example.myhome.ui.view.activity.main.MainActivity
import com.example.myhome.ui.view.fragment.preferences.PreferenceFragment
import com.example.myhome.utils.Utils
import java.lang.IllegalStateException

class DialogOptionMain(private val activity: Activity) : DialogFragment() {

    private val TAG = DialogAbout::class.java.simpleName
    private lateinit var about: TextView
    private val dialogAbout: DialogAbout by lazy {
        DialogAbout()
    }
    private lateinit var preferences: TextView
    private val mainActivity: MainActivity by lazy {
        activity as MainActivity
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity.let {
            val builder = AlertDialog.Builder(it, R.style.MyDialog)

            val view = requireActivity().layoutInflater
                .inflate(R.layout.dialog_option_menu, null)

            builder.setView(view)

            initView(view)

            about.setOnClickListener {
                dialogAbout.show(requireActivity().supportFragmentManager, TAG)
                dialog?.dismiss()
            }

            preferences.setOnClickListener {
                Utils.gotoFragment(
                    mainActivity,
                    R.id.frame_main,
                    PreferenceFragment(),
                    true
                )
                dialog?.dismiss()
            }

            builder.create()
        } ?: throw IllegalStateException("No Activity")
    }

    private fun initView(view: View) {
        about = view.findViewById(R.id.option_about)
        preferences = view.findViewById(R.id.option_preferences)
    }

}