package com.example.myhome.ui.view.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.myhome.R
import java.lang.IllegalStateException

class DialogSelectTypeUser(private val iClickUserType: IClickUserType) : DialogFragment() {

    fun interface IClickUserType {
        fun onClickUserType(type: Int)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            builder.setTitle(getString(R.string.title_set_type_user))
                .setItems(
                    R.array.type_user_array
                ) { dialogInterface, which ->
                    iClickUserType.onClickUserType(which)
                }

            builder.create()
        } ?: throw IllegalStateException("No Activity")
    }
}