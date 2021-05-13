package com.example.myroom.components.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import com.example.myroom.R

class PermissionSelectDialog() : DialogFragment() {
    interface IClickUserShowPermission {
        fun onClickUserPermission(value: Int)
    }

    private lateinit var iClickUserShowPermission: IClickUserShowPermission

    constructor(iClickUserShowPermission: IClickUserShowPermission) : this() {
        this.iClickUserShowPermission = iClickUserShowPermission
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Select Permission")
                .setItems(R.array.permission_array, object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        iClickUserShowPermission.onClickUserPermission(which)
                    }
                })

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}