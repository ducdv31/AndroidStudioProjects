package com.example.myhome.ui.view.dialog.fullscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.myhome.R

class DialogDetailImageFullScreen(
    private val res: String
) : DialogFragment() {

    private lateinit var name: TextView
    private lateinit var btnOk: TextView
    private lateinit var btnCancel: TextView

    override fun onStart() {
        super.onStart()
        dialog?.window?.attributes?.windowAnimations = R.style.dialog_animation_fade
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*
        Theme_DeviceDefault
        Theme_Black_NoTitleBar
        */
        setStyle(
            STYLE_NORMAL,
            android.R.style.Theme_Black_NoTitleBar
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_detail_image_storage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initVar(view)
        startListening()
        logic()
    }

    private fun logic() {
        if (URLUtil.isValidUrl(res)) {
            name.text = URLUtil.guessFileName(res, null, null)
        }
    }

    private fun initVar(view: View) {
        name = view.findViewById(R.id.image_name)
        btnOk = view.findViewById(R.id.btn_ok)
        btnCancel = view.findViewById(R.id.btn_cancel)
    }

    private fun startListening() {
        btnOk.setOnClickListener {

        }

        btnCancel.setOnClickListener {
            dialog?.dismiss()
        }
    }

}