package com.example.myhome.ui.view.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.myhome.R
import java.lang.IllegalStateException

class DialogOptionMain : DialogFragment() {

    private val TAG = DialogAbout::class.java.simpleName
    private lateinit var about: TextView
    private val dialogAbout: DialogAbout by lazy {
        DialogAbout()
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it, R.style.MyDialog)

            val view = requireActivity().layoutInflater
                .inflate(R.layout.dialog_option_menu, null)

            builder.setView(view)

            about = view.findViewById(R.id.option_about)
            about.setOnClickListener {
                dialogAbout.show(requireActivity().supportFragmentManager, TAG)
                dialog?.dismiss()
            }

            builder.create()
        } ?: throw IllegalStateException("No Activity")
    }

}