package vn.dv.todolist.doimain.home.room.dao

import androidx.annotation.WorkerThread
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import vn.dv.todolist.doimain.home.room.entity.CategoryTodoEntity

@Dao
interface CategoryDAO {

    @Query("SELECT * FROM categorytodoentity")
    fun getAllCategory() : List<CategoryTodoEntity>

    @Insert
    fun addCategory(vararg category: CategoryTodoEntity)

    @Delete
    fun deleteCategory(category: CategoryTodoEntity)
}