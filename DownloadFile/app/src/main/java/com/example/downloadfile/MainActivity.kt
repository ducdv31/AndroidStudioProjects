package com.example.downloadfile

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var edUrl: EditText
    lateinit var download: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        edUrl = findViewById(R.id.ed_url)
        download = findViewById(R.id.btn_download)

        edUrl.setText("https://data25.chiasenhac.com/download2/2181/6/2180668-fd76cfc8/128/Thuc%20Giac%20-%20Da%20LAB.mp3")

        download.setOnClickListener {
            downloadFile(edUrl.text.toString().trim())
        }
    }

    private fun downloadFile(url: String) {
        val request: DownloadManager.Request = DownloadManager.Request(Uri.parse(url))
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
        request.setTitle("Download file")
        request.setDescription("Downloading file ...")

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(
            Environment.DIRECTORY_DOWNLOADS,
            System.currentTimeMillis().toString()
        )

        val downloadManager: DownloadManager? =
            getSystemService(Context.DOWNLOAD_SERVICE) as? DownloadManager
        downloadManager?.enqueue(request)
    }
}