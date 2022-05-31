package vn.dv.todolist.app.scenes.home.room.dao

import androidx.room.*
import vn.dv.todolist.app.scenes.home.room.entity.ItemTodoEntity

@Dao
interface ItemTodoDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addItemTodo(vararg itemTodoEntity: ItemTodoEntity)

    @Query("SELECT * FROM itemtodoentity")
    fun getAllTodoItem(): List<ItemTodoEntity>

    @Delete
    fun deleteItemTodo(itemTodoEntity: ItemTodoEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateTodoItem(itemTodoEntity: ItemTodoEntity)
}