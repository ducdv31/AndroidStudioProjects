package com.example.getallvideos

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class VideoAdapter(private var activity: Activity) : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    private lateinit var listVideo: MutableList<VideoModel>

    fun setData(list: MutableList<VideoModel>) {
        this.listVideo = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_video, parent, false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val videoModel = listVideo[position]
        Glide.with(activity).load(videoModel.thumb).into(holder.imgVideo)
    }

    override fun getItemCount(): Int {
        return listVideo.size
    }

    class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgVideo: ImageView = itemView.findViewById(R.id.img_video)
    }
}