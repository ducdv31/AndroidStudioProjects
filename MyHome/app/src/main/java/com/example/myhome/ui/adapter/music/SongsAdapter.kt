package com.example.myhome.ui.adapter.music

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myhome.R
import com.example.myhome.data.model.music.SongItem
import com.makeramen.roundedimageview.RoundedImageView

class SongsAdapter(private val context: Context) :
    RecyclerView.Adapter<SongsAdapter.SongsViewHolder>() {

    private var listSongsItem: MutableList<SongItem> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: MutableList<SongItem>) {
        this.listSongsItem = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_music, parent, false)
        return SongsViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongsViewHolder, position: Int) {
        val itemSong = listSongsItem[position]
        holder.titleSong.text = itemSong.title
        Glide.with(context)
            .load(itemSong.avatar)
            .placeholder(R.drawable.uvv_common_ic_loading_icon)
            .error(R.drawable.outline_error_black_48dp)
            .into(holder.imgAvatar)
    }

    override fun getItemCount(): Int {
        return listSongsItem.size
    }

    class SongsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleSong: TextView = itemView.findViewById(R.id.title_music)
        val imgAvatar: RoundedImageView = itemView.findViewById(R.id.img_avatar_music)
    }
}