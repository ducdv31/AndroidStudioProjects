package vn.dv.todolist.app.ext

import android.view.View

fun View.toInvisible() {
    visibility = View.INVISIBLE
}

fun View.toVisible() {
    visibility = View.VISIBLE
}

fun View.toGone() {
    visibility = View.GONE
}