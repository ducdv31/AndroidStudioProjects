package vn.dv.todolist.doimain.home.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import vn.dv.todolist.doimain.home.room.dao.CategoryDAO
import vn.dv.todolist.doimain.home.room.entity.CategoryTodoEntity

@Database(entities = [CategoryTodoEntity::class], version = 1)
abstract class CategoryTodoDb : RoomDatabase() {
    abstract fun getCategoryTodoDao(): CategoryDAO
}