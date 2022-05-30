package vn.dv.fpttoweropeningdemo.youtubeplayer

import android.os.Bundle
import android.view.View
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import vn.dv.fpttoweropeningdemo.R

class YoutubeActivity : YouTubeBaseActivity() {

    private val youtubePlayerView by lazy { findViewById<YouTubePlayerView>(R.id.youtubePlayerView) }
    private val btnPlay by lazy { findViewById<View>(R.id.btnPlay) }

    private val onInitializedListener: YouTubePlayer.OnInitializedListener by lazy {
        object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(
                provider: YouTubePlayer.Provider?,
                youTubePlayer: YouTubePlayer?,
                p2: Boolean
            ) {
//                youTubePlayer?.loadVideo("mfwSp5nHFzM")
                youTubePlayer?.loadVideo("GyV9oqbRvlo")
            }

            override fun onInitializationFailure(
                p0: YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) {
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_youtube)

        btnPlay.setOnClickListener {
            youtubePlayerView.initialize(
                "AIzaSyBOpbMW7-S39id5Fjj40yiDwLeaixJiYEw",
                onInitializedListener
            )
        }
    }
}