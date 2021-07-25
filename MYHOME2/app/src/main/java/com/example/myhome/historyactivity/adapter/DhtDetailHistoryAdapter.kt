package com.example.myhome.historyactivity.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myhome.R
import com.example.myhome.historyactivity.model.DhtTimeValueModel

class DhtDetailHistoryAdapter :
    RecyclerView.Adapter<DhtDetailHistoryAdapter.DetailHistoryViewHolder>() {

    private var listDetailData: MutableList<DhtTimeValueModel> = mutableListOf()

    fun setData(list: MutableList<DhtTimeValueModel>) {
        this.listDetailData = list
        listDetailData.reverse()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailHistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_data_history_dht11, parent, false)
        return DetailHistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: DetailHistoryViewHolder, position: Int) {
        val dhtTimeValueModel = listDetailData[position]
        holder.time.text = dhtTimeValueModel.getTimeFormatted()
        holder.tValue.text = dhtTimeValueModel.dht11Value?.t.toString()
        holder.hValue.text = dhtTimeValueModel.dht11Value?.h.toString()
    }

    override fun getItemCount(): Int {
        return listDetailData.size
    }

    class DetailHistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val time: TextView = itemView.findViewById(R.id.time)
        val tValue: TextView = itemView.findViewById(R.id.temperature_history)
        val hValue: TextView = itemView.findViewById(R.id.humidity_history)
    }
}