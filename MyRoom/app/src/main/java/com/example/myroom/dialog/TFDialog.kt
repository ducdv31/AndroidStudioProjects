package com.example.myroom.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class TFDialog() : DialogFragment() {

    interface IDialogResponse {
        fun onDialogResponse(response: Boolean)
    }

    lateinit var iDialogResponse: IDialogResponse

    constructor(context: Context, iDialogResponse: IDialogResponse) : this() {
        this.iDialogResponse = iDialogResponse
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage("Are you sure?")
                .setPositiveButton("Yes"
                ) { dialog, which -> iDialogResponse.onDialogResponse(true) }
                .setNegativeButton("No"
                ) { dialog, which -> iDialogResponse.onDialogResponse(false) }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}