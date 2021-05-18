package com.example.myroom.activitycalendar.rcvadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.SwipeRevealLayout
import com.example.myroom.R
import com.example.myroom.activitycalendar.model.TaskView

class RcvTaskAdapter() : RecyclerView.Adapter<RcvTaskAdapter.TaskViewHolder>() {
    private var listTask: MutableList<TaskView> = mutableListOf()

    interface IClickCheck {
        fun onClickCheck(taskView: TaskView, position: Int)
        fun setOnDeleteTask(taskView: TaskView, position: Int)
        fun setOnEditTask(
            taskView: TaskView,
            position: Int,
            oldData: String,
            swipeRevealLayout: SwipeRevealLayout
        )

        fun onSwipeAble(swipeRevealLayout: SwipeRevealLayout)
    }

    private lateinit var iClickCheck: IClickCheck

    constructor(context: Context, iClickCheck: IClickCheck) : this() {
        this.iClickCheck = iClickCheck
    }

    fun setData(list: MutableList<TaskView>) {
        this.listTask = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val taskView = listTask[position] ?: return
        holder.isCompleted.isChecked = taskView.completed
        holder.tvTask.text = taskView.task
        holder.isCompleted.setOnClickListener {
            iClickCheck.onClickCheck(taskView, position)
        }
        holder.deleteTask.setOnClickListener {
            iClickCheck.setOnDeleteTask(taskView, position)
        }
        holder.editTask.setOnClickListener {
            iClickCheck.setOnEditTask(
                taskView,
                position,
                holder.tvTask.text.toString(),
                holder.swipeRevealLayout
            )
        }
        iClickCheck.onSwipeAble(holder.swipeRevealLayout)
    }

    override fun getItemCount(): Int {
        return listTask.size
    }

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val isCompleted: CheckBox = itemView.findViewById(R.id.check_completed)
        val tvTask: TextView = itemView.findViewById(R.id.tv_task)
        val deleteTask: ImageView = itemView.findViewById(R.id.delete_task)
        val editTask: ImageView = itemView.findViewById(R.id.edit_task)
        val swipeRevealLayout: SwipeRevealLayout =
            itemView.findViewById(R.id.swipeReveal_task_item_user)
    }
}