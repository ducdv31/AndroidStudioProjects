package vn.dv.todolist.app.scenes.home

import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import vn.dv.todolist.R
import vn.dv.todolist.app.base.BaseFragment
import vn.dv.todolist.app.scenes.detailltodo.adapter.RecyclerViewItemTouchHelper
import vn.dv.todolist.app.scenes.detailltodo.adapter.TodoAdapter
import vn.dv.todolist.app.scenes.detailltodo.model.TodoModel
import vn.dv.todolist.app.scenes.home.room.db.TodoItemDb
import vn.dv.todolist.app.scenes.home.room.dto.TodoItemDto
import vn.dv.todolist.app.scenes.home.room.entity.ItemTodoEntity
import vn.dv.todolist.databinding.FragmentHomeScenesBinding
import vn.dv.todolist.infrastructure.core.recyclerview.VerticalDpDivider
import javax.inject.Inject

@AndroidEntryPoint
class HomeScenes : BaseFragment<FragmentHomeScenesBinding>(FragmentHomeScenesBinding::inflate) {

    private val ioLauncher = CoroutineScope(IO)

    @Inject
    lateinit var todoDb: TodoItemDb

    private val dialogInputCategory: DialogInputCategory by lazy {
        DialogInputCategory(
            getString(R.string.title_input_todo_name),
            onClickOk = { todoName ->
                if (todoName?.isNotEmpty() == true) {
                    ioLauncher.launch {
                        todoDb
                            .getTodoDao()
                            .addItemTodo(ItemTodoEntity(titleTodo = todoName))
                        loadAllDataFromDb()
                    }
                }
            }
        )
    }

    private val todoAdapter: TodoAdapter by lazy {
        TodoAdapter(
            onCheckItem = { isChecked, todoModel ->
                todoDb.getTodoDao()
                    .updateTodoItem(
                        ItemTodoEntity(
                            idItemTodo = todoModel.id,
                            titleTodo = todoModel.title,
                            isChecked = isChecked
                        )
                    )
            }
        )
    }

    override fun initData(data: Bundle?) {
    }

    override fun initViews() {
        showToolBar(getString(R.string.app_name))
        initRecyclerview()
        loadAllDataFromDb()
    }

    override fun initActions() {
    }

    override fun initListener() {
        binding.btnAddList.setOnClickListener {
            if (!dialogInputCategory.isAdded) {
                dialogInputCategory.show(parentFragmentManager, null)
            }
        }
    }

    override fun initObservers() {
    }

    private fun loadAllDataFromDb() {
        ioLauncher.launch {
            val listData = todoDb.getTodoDao().getAllTodoItem()
            withContext(Main) {
                TodoItemDto.toTodoItem(listData)?.let { todoAdapter.setData(it.toMutableList()) }
            }
        }
    }

    private fun initRecyclerview() {
        binding.rvTodo.apply {
            adapter = todoAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(VerticalDpDivider(SPACE_ITEM_CATEGORY, resources))
        }
        val itemHelperCallback = RecyclerViewItemTouchHelper(
            0,
            ItemTouchHelper.LEFT
        ) { viewHolder ->
            val pos = viewHolder.absoluteAdapterPosition
            CoroutineScope(IO).launch {
                val todoModelDelete = getListTodoFromDb()?.get(pos)
                withContext(Main) {
                    todoModelDelete?.let {
                        todoAdapter removeItemAt pos
                        var deleteItem = true
                        showSnackBarWithAction(
                            message = "Deleted ${todoModelDelete.title}",
                            actionText = "Undo",
                            onClickAction = {
                                todoAdapter.undoItem(todoModelDelete, pos)
                                deleteItem = false
                            },
                            onDismiss = { transientBottomBar, event ->
                                if (deleteItem) {
                                    withContext(IO) {
                                        todoDb.getTodoDao()
                                            .deleteItemTodo(
                                                ItemTodoEntity(
                                                    idItemTodo = todoModelDelete.id,
                                                    titleTodo = todoModelDelete.title,
                                                    isChecked = todoModelDelete.checked
                                                )
                                            )
                                    }
                                }
                            }
                        )
                    } ?: run {
                        showSnackBar(
                            message = "Error"
                        )
                    }
                }
            }
        }
        ItemTouchHelper(itemHelperCallback).attachToRecyclerView(binding.rvTodo)
    }

    private suspend fun getListTodoFromDb(): List<TodoModel>? = withContext(IO) {
        val listTodoDb = todoDb.getTodoDao().getAllTodoItem()
        TodoItemDto.toTodoItem(listTodoDb)
    }

    companion object {
        const val SPACE_ITEM_CATEGORY = 8f
    }
}