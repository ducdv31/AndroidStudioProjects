package vn.dv.todolist.app.scenes.detailltodo.adapter

import androidx.recyclerview.widget.RecyclerView

fun interface ItemTouchHelperListener {
    fun onSwiped(viewHolder: RecyclerView.ViewHolder)
}