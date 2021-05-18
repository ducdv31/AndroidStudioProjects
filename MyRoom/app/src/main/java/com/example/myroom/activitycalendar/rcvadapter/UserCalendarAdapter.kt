package com.example.myroom.activitycalendar.rcvadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.myroom.R
import com.example.myroom.activitycalendar.model.TaskUser
import com.example.myroom.activitycalendar.model.UserCalendar

class UserCalendarAdapter() : RecyclerView.Adapter<UserCalendarAdapter.UserCalendarViewHolder>() {
    var listUsers: MutableList<UserCalendar>
    interface IClickUserCalendar{
        fun onClickUserCalendar(taskUser: TaskUser)
    }

    private lateinit var iClickUserCalendar: IClickUserCalendar

    init {
        listUsers = mutableListOf()
    }

    constructor(context: Context, iClickUserCalendar: IClickUserCalendar) : this() {
        this.iClickUserCalendar = iClickUserCalendar
    }

    fun setData(list: MutableList<UserCalendar>){
        listUsers = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserCalendarViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user_calendar, parent, false)
        return UserCalendarViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserCalendarViewHolder, position: Int) {
        val userCalendar: UserCalendar = listUsers[position] ?: return
        holder.name.text = userCalendar.Name
        holder.cardView.setOnClickListener(View.OnClickListener {
            /* open detail user fragment */
            iClickUserCalendar.onClickUserCalendar(TaskUser(userCalendar.ID, userCalendar.Name))
        })
        holder.start.text = userCalendar.Start
        holder.stop.text = userCalendar.Stop
    }

    override fun getItemCount(): Int {
        return listUsers.size ?: return 0
    }

    class UserCalendarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.user_name_calendar)
        val cardView: CardView = itemView.findViewById(R.id.cardView_user_calendar)
        val start: TextView = itemView.findViewById(R.id.start_time_calendar)
        val stop: TextView = itemView.findViewById(R.id.stop_time_calendar)
    }
}