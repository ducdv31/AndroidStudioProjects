package com.example.myhome.utils.playmedia

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.myhome.R
import com.example.myhome.utils.Constants
import hb.xvideoplayer.MxVideoPlayer
import hb.xvideoplayer.MxVideoPlayerWidget

class PlayMedialMxActivity : AppCompatActivity() {

    private lateinit var mxVideoPlayer: MxVideoPlayerWidget
    private lateinit var rvListVideo: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_medial_mx)

        initVar()
        listenOrientation()

        getResAndPlay(intent)
    }

    private fun getResAndPlay(intent: Intent?) {
        val res: String = intent?.getStringExtra(Constants.VIDEO_KEY) ?: Constants.EMPTY
        if (URLUtil.isValidUrl(res)) {
            mxVideoPlayer.startPlay(
                res,
                MxVideoPlayer.SCREEN_LAYOUT_NORMAL,
                URLUtil.guessFileName(res, null, null)
            )
        }
    }

    private fun initVar() {
        mxVideoPlayer = findViewById(R.id.video_view)
        rvListVideo = findViewById(R.id.rv_list_video_play)
    }

    private fun listenOrientation() {
        if (Configuration.ORIENTATION_LANDSCAPE == resources.configuration.orientation) {
            val layoutParams = mxVideoPlayer.layoutParams
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
            mxVideoPlayer.layoutParams = layoutParams
            rvListVideo.visibility = View.GONE
        } else {
            val layoutParams = mxVideoPlayer.layoutParams
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams.height = 400
            mxVideoPlayer.layoutParams = layoutParams
            rvListVideo.visibility = View.VISIBLE
        }
    }
}