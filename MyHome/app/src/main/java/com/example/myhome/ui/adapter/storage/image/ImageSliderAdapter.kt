package com.example.myhome.ui.adapter.storage.image

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myhome.R

class ImageSliderAdapter(private val context: Context, private val listRes: MutableList<String>) :
    RecyclerView.Adapter<ImageSliderAdapter.ImageSliderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageSliderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_image_slider_storage, parent, false)
        return ImageSliderViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageSliderViewHolder, position: Int) {
        val res = listRes[position]
        Glide.with(context)
            .load(res)
            .placeholder(R.drawable.uvv_common_ic_loading_icon)
            .error(R.drawable.outline_error_black_48dp)
            .into(holder.image)
    }

    override fun getItemCount(): Int {
        return listRes.size
    }

    class ImageSliderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.image_slider_storage)
    }

}