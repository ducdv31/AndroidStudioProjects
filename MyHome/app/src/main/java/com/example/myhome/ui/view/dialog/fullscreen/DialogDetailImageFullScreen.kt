package com.example.myhome.ui.view.dialog.fullscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.myhome.R
import com.makeramen.roundedimageview.RoundedImageView

class DialogDetailImageFullScreen(
    private val res: String
) : DialogFragment() {

    private lateinit var name: TextView
    private lateinit var btnOk: TextView
    private lateinit var btnCancel: TextView
    private lateinit var mRoundedImageView: RoundedImageView

    override fun onStart() {
        super.onStart()
        dialog?.window?.attributes?.windowAnimations = R.style.dialog_animation_fade
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*
        Theme_DeviceDefault
        Theme_Black_NoTitleBar
        Theme_Material
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
            Glide.with(requireContext())
                .load(res)
                .placeholder(R.drawable.uvv_common_ic_loading_icon)
                .error(R.drawable.outline_error_black_48dp)
                .into(mRoundedImageView)
            name.text = URLUtil.guessFileName(res, null, null)
        }
    }

    private fun initVar(view: View) {
        name = view.findViewById(R.id.image_name)
        btnOk = view.findViewById(R.id.btn_ok)
        btnCancel = view.findViewById(R.id.btn_cancel)
        mRoundedImageView = view.findViewById(R.id.image_detail)
    }

    private fun startListening() {
        btnOk.setOnClickListener {

        }

        btnCancel.setOnClickListener {
            dialog?.dismiss()
        }
    }

}