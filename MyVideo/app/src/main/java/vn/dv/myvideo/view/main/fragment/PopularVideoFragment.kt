package vn.dv.myvideo.view.main.fragment

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import dagger.hilt.android.AndroidEntryPoint
import vn.dv.myvideo.common.BaseFragment
import vn.dv.myvideo.common.divider.VerticalDpDivider
import vn.dv.myvideo.databinding.FragmentPopularVideoBinding
import vn.dv.myvideo.view.main.adapter.VideosPexelsAdapter
import vn.dv.myvideo.view.main.model.VideosPexels
import vn.dv.myvideo.view.main.viewmodel.VideoPexelsViewModel
import javax.inject.Inject


@AndroidEntryPoint
class PopularVideoFragment :
    BaseFragment<FragmentPopularVideoBinding>(FragmentPopularVideoBinding::inflate) {

    private val exoPlayer: ExoPlayer by lazy {
        ExoPlayer.Builder(requireContext()).build()
    }
    private val verticalSpaceItem = 24

    private val videoPexelsViewModel: VideoPexelsViewModel by viewModels()

    @Inject
    lateinit var videosPexelsAdapter: VideosPexelsAdapter

    override fun initData() {
    }

    override fun listener() {
        videoPexelsViewModel.videos.observe(this) { listVideoPexels ->
            listVideoPexels?.let {
                videosPexelsAdapter setData it.toMutableList()
            }
        }
        binding.rvVideoPopular.apply {
            adapter = videosPexelsAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(VerticalDpDivider(resources, verticalSpaceItem))
        }
        videosPexelsAdapter.addOnClickItemListener {
            playVideo(it)
        }
        binding.exoPlayer.apply {
            player = exoPlayer
        }
    }

    override fun requestData() {
        videoPexelsViewModel.getPopularVideo()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        exoPlayer.release()
    }

    private fun playVideo(videosPexels: VideosPexels?) {
        videosPexels?.videoFiles?.get(1)?.link?.let {
            val mediaItem = MediaItem.fromUri(it)
            exoPlayer.apply {
                setMediaItem(mediaItem)
                prepare()
                playWhenReady = true
                addListener(object : Player.Listener {
                    override fun onPlaybackStateChanged(playbackState: Int) {
                        super.onPlaybackStateChanged(playbackState)
                        when (playbackState) {
                            Player.STATE_BUFFERING -> {
                                binding.progressLoadVideo.isVisible = true
                            }
                            Player.STATE_READY -> {
                                binding.progressLoadVideo.isVisible = false
                            }
                        }
                    }
                })
            }
        }

    }
}