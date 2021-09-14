package com.example.myhome.ui.view.activity.storage

import android.os.Bundle
import com.example.myhome.BaseActivity
import com.example.myhome.R
import com.example.myhome.ui.view.fragment.storage.PdfStorageFragment


class StorageActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storage_main)
        setTitleActionBar(getString(R.string.storage))
        isShowUserImg(false)

        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.frame_storage, PdfStorageFragment())
        ft.commit()
    }
}