package vn.dv.todolist.app.scenes.detailltodo.model

data class TodoModel(
    var id: Int,
    var checked: Boolean = false,
    var title: String
)