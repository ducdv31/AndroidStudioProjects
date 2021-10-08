package com.example.myhome.ui.view.dialog.bottom

import android.content.Context
import android.os.Bundle
import android.webkit.URLUtil
import android.widget.TextView
import com.example.myhome.R
import com.google.android.material.bottomsheet.BottomSheetDialog

class DetailImageDialog(context: Context, theme: Int,  private val res: String) :
    BottomSheetDialog(context, theme) {
    private var name: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_detail_image_storage)
        name = findViewById(R.id.image_detail)

        name?.text = URLUtil.guessFileName(res, null, null)
    }
}