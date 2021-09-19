package com.example.myhome.utils.showimage

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myhome.utils.Constants
import karacken.curl.PageCurlAdapter
import karacken.curl.PageSurfaceView

class PlayImageBookAnimActivity : AppCompatActivity() {
    private lateinit var surfaceView: PageSurfaceView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initVar()

        getListRes(intent)

    }

    private fun getListRes(intent: Intent) {

        val listRes: ArrayList<String> =
            intent.getStringArrayListExtra(Constants.LIST_IMAGE_RES_KEY) ?: arrayListOf()
        surfaceView.pageCurlAdapter = PageCurlAdapter(listRes.toArray() as Array<out String>)
        setContentView(surfaceView)
    }

    private fun initVar() {
        surfaceView = PageSurfaceView(this)
    }
}