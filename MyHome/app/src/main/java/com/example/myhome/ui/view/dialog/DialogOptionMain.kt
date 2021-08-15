package com.example.myhome.ui.view.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.myhome.R
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.dialog_option_menu.*
import java.lang.IllegalStateException

class DialogOptionMain : DialogFragment() {

    private lateinit var about: TextView

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it, R.style.MyDialog)

            val view = requireActivity().layoutInflater
                .inflate(R.layout.dialog_option_menu, null)

            builder.setView(view)

            about = view.findViewById(R.id.option_about)
            about.setOnClickListener {
                Toast.makeText(requireContext(), "About", Toast.LENGTH_SHORT).show()
                dialog?.dismiss()
            }

            builder.create()
        } ?: throw IllegalStateException("No Activity")
    }


    override fun onDetach() {
        super.onDetach()
        this.clearFindViewByIdCache()
    }
}