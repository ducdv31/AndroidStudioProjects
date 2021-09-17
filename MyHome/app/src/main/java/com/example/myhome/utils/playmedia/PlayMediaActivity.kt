package com.example.myhome.utils.playmedia

import android.content.Intent
import android.content.res.Configuration
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.myhome.R
import com.example.myhome.utils.Constants
import com.universalvideoview.UniversalMediaController
import com.universalvideoview.UniversalVideoView

class PlayMediaActivity : AppCompatActivity() {

    private lateinit var lnPlayMedia: LinearLayout
    private lateinit var rvListMedia: RecyclerView
    private lateinit var universalVideoView: UniversalVideoView
    private lateinit var universalMediaController: UniversalMediaController
    private lateinit var frVideoLayout: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_media)

        initVar()

        startListening()
        handleLogic()

        loadMedia(intent)
    }

    private fun initVar() {
        frVideoLayout = findViewById(R.id.video_layout)
        lnPlayMedia = findViewById(R.id.ln_play_media)
        rvListMedia = findViewById(R.id.rv_list_video_play)
        universalVideoView = findViewById(R.id.videoView)
        universalMediaController = findViewById(R.id.media_controller)
        universalVideoView.setMediaController(universalMediaController)
    }

    private fun loadMedia(intent: Intent) {
        val res: String = intent.getStringExtra(Constants.VIDEO_KEY) ?: Constants.EMPTY
        universalMediaController.setTitle(URLUtil.guessFileName(res, null, null))
        universalVideoView.setVideoPath(res)
        universalVideoView.start()
    }

    private fun handleLogic(){
        if (Configuration.ORIENTATION_LANDSCAPE == resources.configuration.orientation){
            val layoutParams: ViewGroup.LayoutParams = frVideoLayout.layoutParams
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
            frVideoLayout.layoutParams = layoutParams
            rvListMedia.visibility = View.GONE
        } else {
            val layoutParams: ViewGroup.LayoutParams = frVideoLayout.layoutParams
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams.height = 400
            frVideoLayout.layoutParams = layoutParams
            rvListMedia.visibility = View.VISIBLE
        }
    }

    private fun startListening() {
        universalVideoView.setVideoViewCallback(object : UniversalVideoView.VideoViewCallback {
            override fun onScaleChange(isFullscreen: Boolean) {

            }

            override fun onPause(mediaPlayer: MediaPlayer?) {

            }

            override fun onStart(mediaPlayer: MediaPlayer?) {

            }

            override fun onBufferingStart(mediaPlayer: MediaPlayer?) {

            }

            override fun onBufferingEnd(mediaPlayer: MediaPlayer?) {

            }

        })
    }
}