package vn.dv.todolist.doimain.detailtodo.room.dao

import androidx.room.*
import vn.dv.todolist.doimain.detailtodo.room.entity.ItemTodoEntity

@Dao
interface ItemTodoDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addItemTodo(vararg itemTodoEntity: ItemTodoEntity)

    @Query("SELECT * FROM itemtodoentity WHERE id_category == :idCate")
    fun getAllTodoItem( idCate: Int): List<ItemTodoEntity>

    @Delete
    fun deleteItemTodo(itemTodoEntity: ItemTodoEntity)
}