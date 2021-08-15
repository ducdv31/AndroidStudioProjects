package com.example.myhome.ui.view.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.example.myhome.R
import java.lang.IllegalStateException

class DialogAbout : DialogFragment() {

    private lateinit var back: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog?.setCanceledOnTouchOutside(false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it, R.style.MyDialog)

            val view = requireActivity().layoutInflater.inflate(R.layout.dialog_about, null)
            builder.setView(view)

            back = view.findViewById(R.id.btn_back)
            back.setOnClickListener {
                if (dialog?.isShowing == true){
                    dialog?.dismiss()
                }
            }


            builder.create()
        } ?: throw IllegalStateException("No activity")
    }
}