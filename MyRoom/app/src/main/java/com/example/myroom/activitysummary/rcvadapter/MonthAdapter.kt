package com.example.myroom.activitysummary.rcvadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.myroom.R

class MonthAdapter() : RecyclerView.Adapter<MonthAdapter.MonthViewHolder>() {
    lateinit var listMonth: MutableList<Int>

    public interface IClickUserInMonth {
        fun onClickUser(month: String)
    }

    private lateinit var iClickUserInMonth: IClickUserInMonth

    constructor(context: Context, iClickUserInMonth: IClickUserInMonth) : this() {

        this.iClickUserInMonth = iClickUserInMonth
    }

    fun setData(list: MutableList<Int>) {
        listMonth = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_month_number, parent, false)
        return MonthViewHolder(view)
    }

    override fun onBindViewHolder(holder: MonthViewHolder, position: Int) {
        val month: Int = listMonth[position] ?: return
        holder.month.text = month.toString()
        holder.cardMonth.setOnClickListener(View.OnClickListener {
            /* open user of month */
            val Month = if (month < 10) {
                "0$month"
            } else {
                month.toString()
            }
            iClickUserInMonth.onClickUser(Month)
        })
    }

    override fun getItemCount(): Int {
        if (listMonth.isNullOrEmpty()) {
            return 0
        }
        return listMonth.size
    }

    class MonthViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val month: TextView = itemView.findViewById(R.id.month_number)
        val cardMonth: CardView = itemView.findViewById(R.id.cardView_month_number)
    }
}