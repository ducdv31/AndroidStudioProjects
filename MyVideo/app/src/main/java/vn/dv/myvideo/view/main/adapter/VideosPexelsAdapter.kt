package vn.dv.myvideo.view.main.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.imageview.ShapeableImageView
import dagger.hilt.android.qualifiers.ActivityContext
import vn.dv.myvideo.R
import vn.dv.myvideo.view.main.model.VideosPexels
import javax.inject.Inject

class VideosPexelsAdapter @Inject constructor(
    @ActivityContext private val context: Context
) : RecyclerView.Adapter<VideosPexelsAdapter.VideosVH>() {

    private var listVideos: MutableList<VideosPexels> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    infix fun setData(list: MutableList<VideosPexels>) {
        this.listVideos = list
        notifyDataSetChanged()
    }

    private var onClickItem: (VideosPexels?) -> Unit = {}

    fun addOnClickItemListener(onClickItem: (VideosPexels?) -> Unit) {
        this.onClickItem = onClickItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideosVH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_video_pexels, parent, false)
        return VideosVH(view)
    }

    override fun onBindViewHolder(holder: VideosVH, position: Int) {
        val video = listVideos[position]
        holder.setDetailItem(video, onClickItem = this.onClickItem)
    }

    override fun getItemCount(): Int = listVideos.size

    inner class VideosVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemVideo: View = itemView.findViewById(R.id.item_video)
        private val thumbnail: ShapeableImageView = itemView.findViewById(R.id.thumbnail_videos)

        fun setDetailItem(videosPexels: VideosPexels?, onClickItem: (VideosPexels?) -> Unit) {
            if (URLUtil.isValidUrl(videosPexels?.image)) {
                Glide.with(context)
                    .load(videosPexels?.image)
                    .placeholder(R.drawable.ic_videocam_black_24dp)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(thumbnail)
            } else {
                thumbnail.setImageResource(R.drawable.ic_videocam_black_24dp)
            }
            itemVideo.setOnClickListener {
                onClickItem(videosPexels)
            }
        }
    }
}