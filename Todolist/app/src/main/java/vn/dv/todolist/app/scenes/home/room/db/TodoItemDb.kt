package vn.dv.todolist.app.scenes.home.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import vn.dv.todolist.app.scenes.home.room.dao.ItemTodoDAO
import vn.dv.todolist.app.scenes.home.room.entity.ItemTodoEntity

@Database(entities = [ItemTodoEntity::class], version = 1)
abstract class TodoItemDb : RoomDatabase() {
    abstract fun getTodoDao(): ItemTodoDAO
}