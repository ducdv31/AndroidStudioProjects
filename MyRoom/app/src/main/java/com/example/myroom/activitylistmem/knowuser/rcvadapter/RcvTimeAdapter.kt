package com.example.myroom.activitylistmem.knowuser.rcvadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myroom.R
import com.example.myroom.activitylistmem.model.WorkFlow

class RcvTimeAdapter() : RecyclerView.Adapter<RcvTimeAdapter.TimeViewHolder>() {
    private var listTime: MutableList<WorkFlow> = mutableListOf()

    constructor(context: Context) : this() {

    }

    public fun setData(list: MutableList<WorkFlow>) {
        listTime = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_time_work, parent, false)
        return TimeViewHolder(view)
    }

    override fun onBindViewHolder(holder: TimeViewHolder, position: Int) {
        val workFlow: WorkFlow = listTime[position]?: return
        holder.day.text =  workFlow.getDay()
        holder.start.text =  workFlow.getStart()
        holder.end.text =  workFlow.getEnd()
    }

    override fun getItemCount(): Int {
        return listTime.size
    }

    public class TimeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val day: TextView = itemView.findViewById<TextView?>(R.id.date_work)
        val start: TextView = itemView.findViewById<TextView?>(R.id.start_work)
        val end: TextView = itemView.findViewById<TextView?>(R.id.end_work)
    }
}