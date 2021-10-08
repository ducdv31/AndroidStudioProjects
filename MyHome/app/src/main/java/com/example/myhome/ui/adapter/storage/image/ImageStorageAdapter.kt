package com.example.myhome.ui.adapter.storage.image

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.myhome.R

class ImageStorageAdapter(
    private val context: Context,
    private val onClickItem: ((listRes: MutableList<String>, position: Int) -> Unit) = { _, _ ->
    },
    private val onLongClickItem: ((listRes: MutableList<String>, position: Int) -> Unit) = { _, _ ->
    }
) :
    RecyclerView.Adapter<ImageStorageAdapter.ImageStorageViewHolder>() {

    private var listImageRes: MutableList<String> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: MutableList<String>) {
        this.listImageRes = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageStorageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_image_storage, parent, false)
        return ImageStorageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageStorageViewHolder, position: Int) {
        val res = listImageRes[position]
        Glide.with(context)
            .load(res)
            .placeholder(R.drawable.outline_cached_black_48dp)
            .error(R.drawable.outline_error_black_48dp)
            .transform(RoundedCorners(20))
            .into(holder.image)

        holder.lnImage.setOnClickListener {
            onClickItem.invoke(listImageRes, position)
        }
        holder.lnImage.setOnLongClickListener {
            onLongClickItem.invoke(listImageRes, position)
            true
        }
    }

    override fun getItemCount(): Int {
        return listImageRes.size
    }

    class ImageStorageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.image_storage)
        val lnImage: LinearLayout = itemView.findViewById(R.id.ln_image_storage)
    }
}