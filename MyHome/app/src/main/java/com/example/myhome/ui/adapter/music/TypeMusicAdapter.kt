package com.example.myhome.ui.adapter.music

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myhome.R
import com.example.myhome.data.model.music.SongItem
import com.example.myhome.data.model.music.TypeSongs

class TypeMusicAdapter(private val context: Context) : RecyclerView.Adapter<TypeMusicAdapter.ViewHolder>() {

    private var listTypeMusic: MutableList<TypeSongs> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: MutableList<TypeSongs>) {
        this.listTypeMusic = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_type_music, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val typeSongs = listTypeMusic[position]
        holder.nameTypeMusic.text = typeSongs.name
        val llmn = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        val songsAdapter = SongsAdapter(context)
        holder.rvListSong.apply {
            layoutManager = llmn
            adapter = songsAdapter
        }
        songsAdapter.setData(typeSongs.songs as MutableList<SongItem>)
    }

    override fun getItemCount(): Int {
        return listTypeMusic.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTypeMusic: TextView = itemView.findViewById(R.id.name_type_music)
        val rvListSong: RecyclerView = itemView.findViewById(R.id.rv_list_song)
    }
}