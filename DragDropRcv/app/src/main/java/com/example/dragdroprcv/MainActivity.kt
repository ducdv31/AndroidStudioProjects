package com.example.dragdroprcv

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var rcvTaskAdapter: RcvTaskAdapter
    private var listTask: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.rcv_drag_drop)
        rcvTaskAdapter = RcvTaskAdapter()
        val linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = rcvTaskAdapter
        val dividerItemDecoration = DividerItemDecoration(this, RecyclerView.VERTICAL)
        recyclerView.addItemDecoration(dividerItemDecoration)

        listTask = getTask()
        rcvTaskAdapter.setData(listTask)

        /* drag and drop */
        val itemTouchHelper = ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val draggedPos: Int = viewHolder.adapterPosition
                val targetPos: Int = target.adapterPosition

                Collections.swap(listTask, draggedPos, targetPos)
                rcvTaskAdapter.notifyItemMoved(draggedPos, targetPos)


                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

            }

        })
        itemTouchHelper.attachToRecyclerView(recyclerView)
        /* ************* */
    }

    private fun getTask(): MutableList<String> {
        val list: MutableList<String> = mutableListOf()
        for (i in 1..20) {
            list.add("Task $i")
        }
        return list
    }

}