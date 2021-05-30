package com.example.bluetooth.dialog

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.bluetooth.R
import com.example.bluetooth.activitymain.MainActivity

class DialogSetting : DialogFragment() {
    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val mainActivity = activity as MainActivity
            val builder = AlertDialog.Builder(it)

            val inflate = requireActivity().layoutInflater
            val view = inflate.inflate(R.layout.setting_dialog, null, false)

            builder.setView(view)

            /* frag content */
            val about:TextView = view.findViewById(R.id.tv_about)


            about.setOnClickListener {
                dialog?.cancel()
                mainActivity.gotoAboutFragment()
            }

            builder.create()
        }!!
    }
}