package com.example.todo

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    lateinit var rcv_todo: RecyclerView
    lateinit var rcv_complete: RecyclerView
    lateinit var linearLayout: LinearLayout
    lateinit var count: TextView

    lateinit var taskAdapter: TaskAdapter
    lateinit var taskAdapterComplete: TaskAdapter

    var listTask: MutableList<Task> = mutableListOf()
    var listTaskComplete: MutableList<Task> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        linearLayout = findViewById(R.id.linear_expand)
        count = findViewById(R.id.count)
        rcv_todo = findViewById(R.id.rcv_todo_task)
        rcv_complete = findViewById(R.id.rcv_task_complete)

        val linearLayoutManager = LinearLayoutManager(
            this,
            RecyclerView.VERTICAL,
            false
        )
        val linearLayoutManager1 = LinearLayoutManager(
            this,
            RecyclerView.VERTICAL,
            false
        )

        taskAdapter = TaskAdapter(object : TaskAdapter.IClickCheck {
            override fun onClickCheck(task: Task, position: Int) {
                /* delete todo task */
                listTask.remove(task)
                taskAdapter.notifyItemRemoved(position)
                taskAdapter.notifyItemRangeRemoved(position, listTask.size)
                 /* add to complete */
                task.isComplete = true
                listTaskComplete.add(task)
                taskAdapterComplete.notifyDataSetChanged()
                /* set count */
                count.text = listTaskComplete.size.toString()

            }

        })

        taskAdapterComplete = TaskAdapter(object : TaskAdapter.IClickCheck {
            override fun onClickCheck(task: Task, position: Int) {
                /* delete complete task */
                listTaskComplete.remove(task)
                taskAdapterComplete.notifyItemRemoved(position)
                listTaskComplete.let { taskAdapterComplete.notifyItemRangeRemoved(position, it.size) }
                /* add to todo */
                task.isComplete = false
                listTask.add(task)
                taskAdapter.notifyDataSetChanged()
                /* set count */
                count.text = listTaskComplete.size.toString()
            }

        })

        rcv_todo.layoutManager = linearLayoutManager
        rcv_todo.adapter = taskAdapter
        rcv_todo.isNestedScrollingEnabled = false
        rcv_todo.isFocusable = false

        rcv_complete.layoutManager = linearLayoutManager1
        rcv_complete.adapter = taskAdapterComplete

        listTask = dataToDo()
        taskAdapter.setData(listTask)

        taskAdapterComplete.setData(listTaskComplete)

    }


    private fun dataToDo(): MutableList<Task> {
        val list: MutableList<Task> = mutableListOf()
        list.add(Task("Task 1", false))
        list.add(Task("Task 2", false))
        list.add(Task("Task 3", false))
        list.add(Task("Task 4", false))
        return list
    }
}