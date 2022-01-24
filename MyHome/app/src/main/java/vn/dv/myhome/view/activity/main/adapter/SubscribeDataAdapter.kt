package vn.dv.myhome.view.activity.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.qualifiers.ActivityContext
import vn.dv.myhome.R
import vn.dv.myhome.view.activity.main.model.SubscribeDataModel
import javax.inject.Inject

class SubscribeDataAdapter @Inject constructor(
    @ActivityContext private val context: Context
) : RecyclerView.Adapter<SubscribeDataAdapter.SubscribeDataVH>() {

    private var listItem: MutableList<SubscribeDataModel> = mutableListOf()

    fun setData(list: MutableList<SubscribeDataModel>) {
        this.listItem = list
        notifyDataSetChanged()
    }

    fun addDataItem(subscribeDataModel: SubscribeDataModel) {
        this.listItem.add(subscribeDataModel)
        notifyDataSetChanged()
    }

    fun addListItem(list: MutableList<SubscribeDataModel>) {
        this.listItem.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubscribeDataVH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_subscribe_data, parent, false)
        return SubscribeDataVH(view)
    }

    override fun onBindViewHolder(holder: SubscribeDataVH, position: Int) {
        val item = listItem[position]
        holder.apply {
            tvTopic.text = item.topic
            tvMessage.text = item.message
            tvTime.text = item.time
        }
    }

    override fun getItemCount(): Int = listItem.size

    inner class SubscribeDataVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val viewSubscribeItem: View = itemView.findViewById(R.id.view_item_subscribe)
        val tvTopic: AppCompatTextView = itemView.findViewById(R.id.tv_topic)
        val tvMessage: AppCompatTextView = itemView.findViewById(R.id.tv_message)
        val tvTime: AppCompatTextView = itemView.findViewById(R.id.tv_time)
    }
}