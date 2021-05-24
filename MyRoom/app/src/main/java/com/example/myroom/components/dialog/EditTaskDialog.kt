package com.example.myroom.components.dialog

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.myroom.R
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EditTaskDialog(
    private val collection: String,
    private val date: String,
    private val oldData: String? = null
) : DialogFragment() {

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {

            val builder = AlertDialog.Builder(it, R.style.Trans20)

            val inflater: LayoutInflater = requireActivity().layoutInflater
            val view: View = inflater.inflate(R.layout.dialog_input_layout, null)

            builder.setView(view)

            val inputText = view.findViewById<EditText>(R.id.editText_task)
            val title = view.findViewById<TextView>(R.id.title_dialog)
            val btCancel = view.findViewById<Button>(R.id.cancel)
            val btOk = view.findViewById<Button>(R.id.ok)
            title.text = "Edit task"
            inputText.setText(oldData)

            btCancel.setOnClickListener {
                dialog?.dismiss()
            }
            btOk.setOnClickListener {
                /* delete old task and set new task */
                val task: String = inputText.text.toString()
                if (task.isNotEmpty()) {
                    val oldMap = hashMapOf<String, Any>(oldData!! to FieldValue.delete())
                    Firebase.firestore.collection(collection)
                        .document(date)
                        .update(oldMap)
                    val addData: Map<String, Boolean> = mapOf(task to true)
                    Firebase.firestore.collection(collection)
                        .document(date)
                        .update(addData)
                    dialog?.dismiss()
                }
            }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}