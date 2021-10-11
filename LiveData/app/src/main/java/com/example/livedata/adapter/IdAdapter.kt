package com.example.livedata.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.livedata.R
import com.example.livedata.model.ID

class IdAdapter(private val context: Context) : RecyclerView.Adapter<IdAdapter.IdViewHolder>() {


    private var listId: ArrayList<ID> = arrayListOf()

    fun setData(list: ArrayList<ID>) {
        listId = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IdViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_id, parent, false)
        return IdViewHolder(view)
    }

    override fun onBindViewHolder(holder: IdViewHolder, position: Int) {
        val id = listId[position]
        holder.a.text = id.a.toString()
        holder.b.text = id.b.toString()
    }

    override fun getItemCount(): Int {
        return listId.size
    }

    class IdViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val a: TextView = itemView.findViewById(R.id.a)
        val b: TextView = itemView.findViewById(R.id.b)
    }
}