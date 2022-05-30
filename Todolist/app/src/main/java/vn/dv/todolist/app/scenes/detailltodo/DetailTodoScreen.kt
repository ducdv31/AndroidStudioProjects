package vn.dv.todolist.app.scenes.detailltodo

import android.os.Bundle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import vn.dv.todolist.R
import vn.dv.todolist.app.base.BaseFragment
import vn.dv.todolist.app.scenes.detailltodo.adapter.RecyclerViewItemTouchHelper
import vn.dv.todolist.app.scenes.detailltodo.adapter.TodoAdapter
import vn.dv.todolist.app.scenes.detailltodo.model.TodoModel
import vn.dv.todolist.databinding.FragmentDetailTodoScreenBinding
import vn.dv.todolist.doimain.detailtodo.room.db.TodoItemDb
import vn.dv.todolist.doimain.detailtodo.room.dto.TodoItemDto
import vn.dv.todolist.infrastructure.core.recyclerview.VerticalDpDivider
import javax.inject.Inject


@AndroidEntryPoint
class DetailTodoScreen :
    BaseFragment<FragmentDetailTodoScreenBinding>(
        FragmentDetailTodoScreenBinding::inflate
    ) {

    @Inject
    lateinit var todoItemDb: TodoItemDb

    private val args: DetailTodoScreenArgs by navArgs()

    private val todoAdapter: TodoAdapter by lazy {
        TodoAdapter(
            onCheckItem = { isChecked, todoModel ->
                CoroutineScope(Main).launch {
                    if (getListDone(args.idCategory)?.contains(todoModel) == true) {
                        todoModel.checked = true
                        val pos = getListNotDone(args.idCategory)?.indexOf(todoModel)
                        listTodo.remove(todoModel)
                        todoAdapter.notifyItemRemoved(pos)
                        listDone.add(todoModel)
                        doneAdapter.notifyItemInserted(listDone.lastIndex)
                    }
                }
            }
        )
    }

    private val doneAdapter: TodoAdapter by lazy {
        TodoAdapter(
            onCheckItem = { isChecked, todoModel ->
                if (listDone.contains(todoModel)) {
                    todoModel.checked = false
                    val pos = listDone.indexOf(todoModel)
                    listDone.remove(todoModel)
                    doneAdapter.notifyItemRemoved(pos)
                    listTodo.add(todoModel)
                    todoAdapter.notifyItemInserted(listTodo.lastIndex)
                }
            }
        )
    }

    override fun initData(data: Bundle?) {
    }

    override fun initViews() {
        showToolBar(
            args.titleCategory,
            R.drawable.ic_baseline_arrow_back_ios_24
        )
        initRecyclerviewTodo()
        initRecyclerviewDone()
    }

    override fun initActions() {
    }

    override fun initListener() {
    }

    override fun initObservers() {
    }

    private fun initRecyclerviewTodo() {
        val itemHelperCallback = RecyclerViewItemTouchHelper(
            0,
            ItemTouchHelper.LEFT
        ) { viewHolder ->
            val pos = viewHolder.absoluteAdapterPosition
            val todoModelDelete = listTodo[pos]
            todoAdapter removeItemAt pos
            Snackbar.make(binding.root, "Deleted ${todoModelDelete.title}", Snackbar.LENGTH_LONG)
                .setAction("Undo") {
                    todoAdapter.undoItem(todoModelDelete, pos)
                }
                .show()
        }
        binding.rvTodoReminder.apply {
            adapter = todoAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(VerticalDpDivider(SPACE_ITEM_TODO, resources))
        }
        ItemTouchHelper(itemHelperCallback).attachToRecyclerView(binding.rvTodoReminder)
        todoAdapter.setData(listTodo)
    }

    private fun initRecyclerviewDone() {
        val itemHelperCallback = RecyclerViewItemTouchHelper(
            0,
            ItemTouchHelper.LEFT
        ) { viewHolder ->
            val pos = viewHolder.absoluteAdapterPosition
            val todoModelDelete = listTodo[pos]
            doneAdapter removeItemAt pos
            Snackbar.make(binding.root, "Deleted ${todoModelDelete.title}", Snackbar.LENGTH_LONG)
                .setAction("Undo") {
                    doneAdapter.undoItem(todoModelDelete, pos)
                }
                .show()
        }
        binding.rvDoneReminder.apply {
            adapter = doneAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(VerticalDpDivider(SPACE_ITEM_TODO, resources))
        }
        ItemTouchHelper(itemHelperCallback).attachToRecyclerView(binding.rvDoneReminder)
        doneAdapter.setData(listDone)
    }

    private suspend fun getListTodo(idCategory: Int): List<TodoModel>? {
        return CoroutineScope(IO).async {
            TodoItemDto.toTodoItem(todoItemDb.getTodoDao().getAllTodoItem(args.idCategory))
                ?.toMutableList()
        }.await()
    }

    private suspend fun getListDone(idCategory: Int): List<TodoModel>? {
        return withContext(IO) {
            val listD = mutableListOf<TodoModel>()
            getListTodo(idCategory)?.forEach {
                if (it.checked) listD.add(it)
            }
            listD
        }
    }

    private suspend fun getListNotDone(idCategory: Int): List<TodoModel>? {
        return withContext(IO) {
            val listNotDone = mutableListOf<TodoModel>()
            getListTodo(idCategory)?.forEach {
                if (!it.checked) listNotDone.add(it)
            }
            listNotDone
        }
    }

    companion object {
        const val SPACE_ITEM_TODO = 8f
    }
}