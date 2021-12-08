package vn.deviot.imageslide.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dagger.hilt.android.qualifiers.ActivityContext
import vn.deviot.imageslide.R
import vn.deviot.imageslide.model.MemeItem
import javax.inject.Inject

class SlideAdapter @Inject constructor(
    @ActivityContext private val context: Context
) : RecyclerView.Adapter<SlideAdapter.SlideVH>() {

    private var listData: MutableList<MemeItem> = mutableListOf()

    fun setData(list: MutableList<MemeItem>) {
        this.listData = list
        notifyDataSetChanged()
    }

    class SlideVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.img)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideVH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_slide, parent, false)
        return SlideVH(view)
    }

    override fun onBindViewHolder(holder: SlideVH, position: Int) {
        val item = listData[position]
        Glide.with(context)
            .load(item.url)
            .into(holder.img)
    }

    override fun getItemCount(): Int = listData.size
}