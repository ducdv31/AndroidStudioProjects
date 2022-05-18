package vn.dv.todolist.doimain.detailtodo.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import vn.dv.todolist.app.const.Const

@Entity
data class ItemTodoEntity(
    @PrimaryKey(autoGenerate = true) val idItemTodo: Int,
    @ColumnInfo(name = Const.ID_CATEGORY) val idCategory: Int,
    @ColumnInfo(name = Const.TITLE_TODO) val titleTodo: String,
    @ColumnInfo(name = Const.IS_CHECKED) val isChecked: Boolean
)