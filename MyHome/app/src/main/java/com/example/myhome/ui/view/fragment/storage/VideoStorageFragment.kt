package com.example.myhome.ui.view.fragment.storage

import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myhome.BaseFragment
import com.example.myhome.R
import com.example.myhome.ui.adapter.storage.video.VideoStorageAdapter
import com.example.myhome.ui.viewmodel.storage.VideoStorageViewModel

class VideoStorageFragment : BaseFragment() {

    private val videoStorageViewHolder: VideoStorageViewModel by lazy {
        ViewModelProvider(requireActivity())[VideoStorageViewModel::class.java]
    }
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var videoStorageAdapter: VideoStorageAdapter

    override fun getLayout(): Int {
        return R.layout.fragment_video_storage
    }

    override fun initVar(rootView: View) {
        recyclerView = rootView.findViewById(R.id.rv_storage)
        progressBar = rootView.findViewById(R.id.progress_bar)
        swipeRefreshLayout = rootView.findViewById(R.id.refresh_container)
        videoStorageAdapter = VideoStorageAdapter(requireContext())
        gridLayoutManager = GridLayoutManager(
            requireContext(),
            2,
            RecyclerView.VERTICAL,
            false
        )

        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter = videoStorageAdapter
    }

    override fun initListener() {
        swipeRefreshLayout.setOnRefreshListener {
            videoStorageViewHolder.getListVideo(videoStorageAdapter, {
                swipeRefreshLayout.isRefreshing = false
            }, {
                swipeRefreshLayout.isRefreshing = false
            })
        }
    }

    override fun handleLogic() {
        showLoading(true)
        videoStorageViewHolder.getListVideo(videoStorageAdapter,
            {
                showLoading(false)
            }, {
                showLoading(false)
            })
    }

    private fun showLoading(isShow: Boolean) {
        if (isShow) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

}