package com.example.getallvideos

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission

class MainActivity : AppCompatActivity() {
    lateinit var videoAdapter: VideoAdapter
    lateinit var getVideo: Button
    lateinit var recyclerView: RecyclerView

    private var listVideo: MutableList<VideoModel> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.rv_video)
        getVideo = findViewById(R.id.btn_get_all_video)
        videoAdapter = VideoAdapter(this)
        val gridLayoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)

        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter = videoAdapter

        getVideo.setOnClickListener {
            checkPerm()
        }
    }

    private fun checkPerm() {
        val permissionlistener: PermissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                getAllVideo()
            }

            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {

            }

        }
        TedPermission.with(this)
            .setPermissionListener(permissionlistener)
            .setDeniedMessage("You are denied")
            .setPermissions(
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .check()
    }

    private fun getAllVideo() {
        var uri: Uri
        var cursor: Cursor
        var columnIndexData: Int
        var thumb: Int

    }
}