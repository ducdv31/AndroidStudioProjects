package com.example.myroom.components.dialog

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.myroom.R
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class InputTaskDialog(val collection: String, val date: String) : DialogFragment() {

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {

            val builder = AlertDialog.Builder(it, R.style.Trans20)

            val inflater: LayoutInflater = requireActivity().layoutInflater
            val view: View = inflater.inflate(R.layout.dialog_input_layout, null)

            builder.setView(view)

            val inputText = view.findViewById<EditText>(R.id.editText_task)
            val btCancel = view.findViewById<Button>(R.id.cancel)
            val btOk = view.findViewById<Button>(R.id.ok)

            btCancel.setOnClickListener {
                dialog?.dismiss()
            }
            btOk.setOnClickListener {
                val task: String = inputText.text.toString()
                if (task.isNotEmpty()) {
                    val addData: Map<String, Boolean> = mapOf(task to true)
                    Firebase.firestore.collection(collection)
                        .document(date)
                        .set(addData, SetOptions.merge())
                    dialog?.dismiss()
                }
            }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}