package com.example.myhome.ui.adapter.history

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myhome.R
import com.example.myhome.data.model.dht.DhtTimeValueModel
import com.example.myhome.utils.Constants
import java.util.*

class DataHistoryAdapter<T>(val context: Context, private val itemType: Int) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var listData: MutableList<T>? = null


    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: MutableList<T>) {
        listData = list
        listData?.reverse()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            Constants.ITEM_TYPE_DHT -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_data_dht_history, parent, false)
                DhtDataHistoryViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_data_dht_history, parent, false)
                DhtDataHistoryViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            Constants.ITEM_TYPE_DHT -> {
                val itemDhtData: DhtTimeValueModel = listData?.get(position) as DhtTimeValueModel
                val dhtDataHistoryViewHolder = holder as DhtDataHistoryViewHolder
                dhtDataHistoryViewHolder.time.text = itemDhtData.getTimeFormatted()
                dhtDataHistoryViewHolder.tValue.text = (itemDhtData.dht11Value?.t ?: 0).toString()
                dhtDataHistoryViewHolder.hValue.text = (itemDhtData.dht11Value?.h ?: 0).toString()
            }
        }
    }

    override fun getItemCount(): Int {
        return listData?.size ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        return itemType
    }

    class DhtDataHistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val time: TextView = itemView.findViewById(R.id.time)
        val tValue: TextView = itemView.findViewById(R.id.temperature_history)
        val hValue: TextView = itemView.findViewById(R.id.humidity_history)
    }
}