package com.example.myhome.historyactivity.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.myhome.R
import com.example.myhome.historyactivity.model.DateHistory

class DhtDateHistoryAdapter(iClickDate: IClickDate) : RecyclerView.Adapter<DhtDateHistoryAdapter.DhtHistoryViewHolder>() {

    fun interface IClickDate{
        fun onClickDate(dateHistory: DateHistory)
    }
    private var iClickDate: IClickDate
    private var listData: MutableList<DateHistory> = mutableListOf()

    init {
        this.iClickDate = iClickDate
    }

    fun setData(list: MutableList<DateHistory>) {
        listData = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DhtHistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_date_history_dht, parent, false)
        return DhtHistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: DhtHistoryViewHolder, position: Int) {
        val dateHistory = listData[position]
        holder.date.text = dateHistory.getDateFormatted()
        holder.lnView.setOnClickListener {
            iClickDate.onClickDate(dateHistory)
        }
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    class DhtHistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date: TextView = itemView.findViewById(R.id.tv_date_dht_history)
        val lnView: LinearLayout = itemView.findViewById(R.id.ln_date_dht_history)
    }
}