package vn.dv.todolist.doimain.detailtodo.room.dto

import vn.dv.todolist.app.scenes.detailltodo.model.TodoModel
import vn.dv.todolist.doimain.detailtodo.room.entity.ItemTodoEntity

object TodoItemDto {
    fun toTodoItem(todoEntity: List<ItemTodoEntity>?): List<TodoModel>? {
        val list = mutableListOf<TodoModel>()
        return todoEntity?.run {
            todoEntity.forEach {
                list.add(
                    TodoModel(
                        it.idItemTodo,
                        it.isChecked,
                        it.titleTodo
                    )
                )
            }
            list
        }
    }
}