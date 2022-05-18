package vn.dv.todolist.doimain.home.room.dto

import vn.dv.todolist.app.scenes.home.model.CategoryReminderModel
import vn.dv.todolist.doimain.home.room.entity.CategoryTodoEntity

fun getCategoryFromEntity(listCategoryTodoEntity: List<CategoryTodoEntity>): List<CategoryReminderModel> {
    val listCategoryModel: MutableList<CategoryReminderModel> = mutableListOf()
    listCategoryTodoEntity.forEach {
        listCategoryModel.add(
            CategoryReminderModel(
                it.idCategory,
                it.title
            )
        )
    }
    return listCategoryModel
}