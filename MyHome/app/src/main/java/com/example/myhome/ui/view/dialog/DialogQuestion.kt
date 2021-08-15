package com.example.myhome.ui.view.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.myhome.R
import java.lang.IllegalStateException

class DialogQuestion(title: String, iClickDialogButton: IClickDialogButton) : DialogFragment() {

    interface IClickDialogButton {
        fun onClickCancel()
        fun onClickOk()
    }

    private val iClickDialogButton = iClickDialogButton
    private val title = title

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            builder.setTitle(title)
            builder.setNegativeButton(
                getString(R.string.cancel),
                object : DialogInterface.OnClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        dialog?.dismiss()
                        iClickDialogButton.onClickCancel()
                    }
                })
            builder.setPositiveButton(
                getString(R.string.ok),
                object : DialogInterface.OnClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        iClickDialogButton.onClickOk()
                    }

                })

            builder.create()
        } ?: throw IllegalStateException("No Activity")
    }

}