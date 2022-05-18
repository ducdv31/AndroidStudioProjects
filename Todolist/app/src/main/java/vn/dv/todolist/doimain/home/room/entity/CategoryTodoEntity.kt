package vn.dv.todolist.doimain.home.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import vn.dv.todolist.app.const.Const

@Entity
data class CategoryTodoEntity(
    @PrimaryKey(autoGenerate = true) val idCategory: Int = 0,
    @ColumnInfo(name = Const.TITLE_CATEGORY) val title: String
)
