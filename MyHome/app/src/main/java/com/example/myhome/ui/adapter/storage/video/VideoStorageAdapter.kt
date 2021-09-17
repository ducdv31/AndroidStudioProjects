package com.example.myhome.ui.adapter.storage.video

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.myhome.R

class VideoStorageAdapter(
    private val context: Context,
    private val onClickItem: ((res: String) -> Unit) = {}
) :
    RecyclerView.Adapter<VideoStorageAdapter.VideoStorageViewHolder>() {

    private var listRes: MutableList<String> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: MutableList<String>) {
        this.listRes = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoStorageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_video_storage, parent, false)
        return VideoStorageViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoStorageViewHolder, position: Int) {
        val res = listRes[position]

        Glide.with(context)
            .load(res)
            .placeholder(R.drawable.outline_cached_black_48dp)
            .error(R.drawable.outline_error_black_48dp)
            .transform(RoundedCorners(20))
            .into(holder.video)

        holder.nameVideo.text = URLUtil.guessFileName(res, null, null)

        holder.rlt_video.setOnClickListener {
            onClickItem.invoke(res)
        }
    }

    override fun getItemCount(): Int {
        return listRes.size
    }

    class VideoStorageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val video: ImageView = itemView.findViewById(R.id.video_storage)
        val nameVideo: TextView = itemView.findViewById(R.id.name_video)
        val rlt_video: RelativeLayout = itemView.findViewById(R.id.rlt_video_storage)
    }
}