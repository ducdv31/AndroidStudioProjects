package com.example.loadmorekotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import javax.inject.Inject

class UserAdapter @Inject constructor() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_ITEM = 1
        const val TYPE_LOADING = 2
    }

    private var listUser: MutableList<String> = mutableListOf()
    private var isLoading: Boolean = false

    fun setData(list: MutableList<String>) {
        this.listUser = list
        notifyDataSetChanged()
    }

    fun addFooterLoading() {
        isLoading = true
        listUser.add("")
    }

    fun removeLoading() {
        isLoading = false
        listUser.removeAt(listUser.size - 1)
        notifyItemRemoved(listUser.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_ITEM -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_user, parent, false)
                UserVH(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_loading, parent, false)
                LoadingVH(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == TYPE_ITEM) {
            val user = listUser[position]
            val userVH: UserVH = holder as UserVH
            userVH.name.text = user + " " + (position + 1)
        }
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    override fun getItemViewType(position: Int): Int {
        if (position == listUser.size - 1 && isLoading) {
            return TYPE_LOADING
        }
        return TYPE_ITEM
    }

    class UserVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tv_user)
    }

    class LoadingVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val progress: ProgressBar = itemView.findViewById(R.id.progress_bar)
    }
}