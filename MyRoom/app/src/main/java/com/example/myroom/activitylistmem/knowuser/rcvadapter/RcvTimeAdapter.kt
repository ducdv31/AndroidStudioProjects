package com.example.myroom.activitylistmem.knowuser.rcvadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myroom.R
import com.example.myroom.activitylistmem.model.WorkFlow
import java.util.*

class RcvTimeAdapter() : RecyclerView.Adapter<RcvTimeAdapter.TimeViewHolder>() {
    private var listTime: MutableList<WorkFlow> = mutableListOf()

    interface ISummaryUser {
        fun onReceivedSummary(minute: Int)
    }

    private lateinit var iSummaryUser: ISummaryUser

    constructor(context: Context, iSummaryUser: ISummaryUser) : this() {
        this.iSummaryUser = iSummaryUser
    }

    fun setData(list: MutableList<WorkFlow>) {
        listTime = list
        listTime.reverse()
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
        holder.end.text = workFlow.getEnd()
        holder.timeLine.text = workFlow.getTimeLineDay()
        iSummaryUser.onReceivedSummary(workFlow.getTimeLineMinutes())
    }

    override fun getItemCount(): Int {
        return listTime.size
    }

    public class TimeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val day: TextView = itemView.findViewById<TextView?>(R.id.date_work)
        val start: TextView = itemView.findViewById<TextView?>(R.id.start_work)
        val end: TextView = itemView.findViewById<TextView?>(R.id.end_work)
        val timeLine: TextView = itemView.findViewById<TextView?>(R.id.time_line)
    }
}