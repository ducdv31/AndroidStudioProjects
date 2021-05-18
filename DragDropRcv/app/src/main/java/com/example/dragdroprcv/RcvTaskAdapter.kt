package com.example.dragdroprcv

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RcvTaskAdapter() : RecyclerView.Adapter<RcvTaskAdapter.TaskViewHolder>() {
    private var listTask: MutableList<String> = mutableListOf()

    constructor(context: Context) : this() {

    }

    fun setData(list: MutableList<String>){
        listTask =  list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task:String = listTask[position] ?: return
        holder.task.text = task
    }

    override fun getItemCount(): Int {
        return listTask.size
    }

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val task = itemView.findViewById<TextView>(R.id.task)
    }
}