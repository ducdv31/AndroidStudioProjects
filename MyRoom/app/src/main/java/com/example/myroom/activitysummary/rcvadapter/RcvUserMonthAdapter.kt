package com.example.myroom.activitysummary.rcvadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myroom.R
import com.example.myroom.activitysummary.model.UserSummary

class RcvUserMonthAdapter() : RecyclerView.Adapter<RcvUserMonthAdapter.UserMonthViewHolder>() {
    lateinit var listUser : MutableList<UserSummary>

    init {
        listUser = mutableListOf()
    }
    constructor(context: Context):this() {

    }

    fun setData(list:MutableList<UserSummary>){
        listUser = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserMonthViewHolder {
        val view =  LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user_month, parent, false)
        return UserMonthViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserMonthViewHolder, position: Int) {
        val userSummary: UserSummary = listUser[position] ?: return
        holder.name.text = userSummary.Name
        holder.all_time.text = userSummary.AllTime

    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    class UserMonthViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tv_name_in_month)
        val all_time: TextView = itemView.findViewById(R.id.total_time_in_month)
    }
}