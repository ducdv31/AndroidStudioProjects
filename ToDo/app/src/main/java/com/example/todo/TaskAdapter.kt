package com.example.todo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter() : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    var listTask: MutableList<Task> = mutableListOf()

    interface IClickCheck {
        fun onClickCheck(task: Task, position: Int)
    }

    lateinit var iClickCheck: IClickCheck

    constructor(iClickCheck: IClickCheck) : this() {
        this.iClickCheck = iClickCheck
    }

    fun setData(list: MutableList<Task>) {
        this.listTask = list
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task: Task = listTask[position] ?: return
        holder.task.text = task.name
        holder.isOk.isChecked = task.isComplete
        holder.isOk.setOnClickListener {
            iClickCheck.onClickCheck(task, position)
        }
    }

    override fun getItemCount(): Int {
        return listTask.size ?: 0
    }

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val isOk: CheckBox = itemView.findViewById(R.id.checkComplete)
        val task: TextView = itemView.findViewById(R.id.task)
    }
}