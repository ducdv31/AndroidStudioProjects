package vn.dv.todolist.app.scenes.home.room.dto

import vn.dv.todolist.app.scenes.detailltodo.model.TodoModel
import vn.dv.todolist.app.scenes.home.room.entity.ItemTodoEntity

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